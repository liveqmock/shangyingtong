package gnete.card.web.report;

import flink.util.DateUtil;
import gnete.card.dao.BranchInfoDAO;
import gnete.card.entity.BranchInfo;
import gnete.card.entity.UserInfo;
import gnete.card.entity.type.RoleType;
import gnete.card.entity.type.SkflReportType;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
  * @Project: Card
  * @File: MerchProxyReturnReportImpl.java
  * @See:
  * @description：
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2011-6-10
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2011 版权所有
  * @version:  V1.0
 */
@Service("cardProxyReturnReport")
public class CardProxyReturnReportImpl extends AbstractCardReportLoadImpl{
	@Autowired
	private BranchInfoDAO branchInfoDAO;

	//private final String REPORT_CARD_FILE = "/fkjgFlMonthDs.raq";

	private final String REPORT_PROXY_FILE = "/cardProxy/skdlFlMonthDs.raq";
	
	private final String REPORT_ERROR_MSG = "没有权限查看售卡返利月报表!";
	
	/**
	 * @description: 
	 *   <li>向页面输出角色类型(roleType)+报表类型(reportTypeList)+发卡或售卡代理列表(branchList or skdlList)+分支机构信息(branchInfo)</li>
	 *   <li>分支机构信息用于当角色为发卡机构或售卡代理角色时自身的机构类信息</li>
	
	 */
	@Override
	protected void processUserLoad(HttpServletRequest request, UserInfo userInfo) {
		String roleType = userInfo.getRole().getRoleType();
		
		List<SkflReportType> skflReportTypeList = new ArrayList<SkflReportType>();
        
		BranchInfo branchInfo = (BranchInfo) branchInfoDAO.findByPk(userInfo.getBranchNo());

		/**
		 * @description: 
		 *   <li>如果是运营中心或者运营中心部门则可以根据类型查找其下所有的类型机构</li> 
		 *   <li>可查看所有的发卡机构或售卡代理的报表类型</li>
		 */
		if (StringUtils.equals(roleType, RoleType.CENTER.getValue())
				|| StringUtils.equals(roleType, RoleType.CENTER_DEPT.getValue())) {

			//List<BranchInfo> branchList = this.branchInfoDAO.findByType(RoleType.CARD.getValue());
			List<BranchInfo> skdlList = this.branchInfoDAO.findByType(RoleType.CARD_SELL.getValue());

			//request.setAttribute("branchList", branchList);
			request.setAttribute("skdlList", skdlList);
			
			//skflReportTypeList.add(SkflReportType.CARD);
			skflReportTypeList.add(SkflReportType.SELL);
		}

		/**
		 * @description: 
		 *   <li>如果是分支机构则查询其管辖下的所有相关类型机构</li> 
		 *   <li>可查看所有的发卡机构或售卡代理的报表类型</li>
		 */
		else if (StringUtils.equals(roleType, RoleType.FENZHI.getValue())) {
			//List<BranchInfo> branchList = branchInfoDAO.findByTypeAndManage(RoleType.CARD.getValue(),userInfo.getBranchNo());
			List<BranchInfo> skdlList = this.branchInfoDAO.findByTypeAndManage(RoleType.CARD_SELL.getValue(),userInfo.getBranchNo());

			//request.setAttribute("branchList", branchList);
			request.setAttribute("skdlList", skdlList);

			//skflReportTypeList.add(SkflReportType.CARD);
			skflReportTypeList.add(SkflReportType.SELL);
		}

		/**
		 * @description:
		 *    <li>如果是发卡机构则可查看售卡代理，发卡机构及其名称则绑定是其自身信息</li>
		 */
		/*
		else if (StringUtils.equals(roleType, RoleType.CARD.getValue())
				|| StringUtils.equals(roleType, RoleType.CARD_DEPT.getValue())) {
			List<BranchInfo> skdlList = this.branchInfoDAO.findCardProxy(userInfo.getBranchNo(), ProxyType.SELL);
			request.setAttribute("skdlList", skdlList);
			skflReportTypeList.add(SkflReportType.CARD_SELL);
			request.setAttribute("branchInfo", branchInfo.getBranchCode() + "|" + branchInfo.getBranchName());
		}*/

		/**
		 * @description:
		 *   <li>如果是售卡代理则可查看分支机构，售卡代理及其名称则绑定其自身</li>
		 */
		else if (StringUtils.equals(roleType, RoleType.CARD_SELL.getValue())) {
			List<BranchInfo> branchList = this.branchInfoDAO.findCardByProxy(userInfo.getBranchNo());
			request.setAttribute("branchList", branchList);
			skflReportTypeList.add(SkflReportType.SELL_CARD);
			request.setAttribute("branchInfo", branchInfo.getBranchCode() + "|" + branchInfo.getBranchName());
		}

		/**
		 * @description:否则页面提示没有权限查看返利报表
		 */
		else {
			request.setAttribute("errMsg", REPORT_ERROR_MSG);
			return;
		}

		request.setAttribute("roleType", roleType);
		
		request.setAttribute("reportTypeList", skflReportTypeList);		
	}

	/**
	 * @description:需注意的是这里拼接的参数需和模板文件中定义的参数保持一致
	 */
	@Override
	protected String[] getLoadQueryParams(String roleType, String reportType, String[] params)
			throws Exception {
		return (checkTopLevel(roleType)) ? getTopLevelQueryParams(roleType,reportType,params) : 
			                                getLowLevelQueryParams(roleType,reportType,params);		
		
	}
	
	/**
	 * 
	  * @description：如果是中心(部门)或分支机构
	  * @param roleType
	  * @param reportType
	  * @param params
	  * @return
	  * @throws Exception  
	  * @version: 2010-12-5 上午10:25:20
	  * @See:
	 */
	private String[] getTopLevelQueryParams(String roleType, String reportType, String[] params) throws Exception{
        String reportFile = null;
		
		StringBuilder searchBuilder = new StringBuilder(100);
		
		String feeDate = params[0];
		
		String branchCode = params[1];
		
		String branchName = getBranchName(branchCode);
		
		//String branchValue = params[1];
		
		//String branchCode = branchValue.split("\\|")[0];

		//String branchName = branchValue.split("\\|")[1];
		
		String[] feeDatePair = DateUtil.getReportDateMonthPare(feeDate);
		/**
		 * @description:且报表类型为发卡机构时，则统计提交发卡机构下所有售卡代理的返利报表
		 */
		/*
		if(StringUtils.equals(reportType, SkflReportType.CARD.getValue())) {
			reportFile = REPORT_CARD_FILE;	
			
			searchBuilder.append("branchCode=" + branchCode).append(";")
			             .append("branchName=" + branchName).append(";");
			searchBuilder.append("feeDate=" + feeDatePair[0]).append(";")
			             .append("_feeDate=" + feeDatePair[1]).append(";");
		}*/
		
		/**
		 * @description:且报表类型为售卡代理时,则统计提交售卡代理关联发卡机构的返利报表
		 */
		if (StringUtils.equals(reportType, SkflReportType.SELL.getValue())) {
			reportFile = REPORT_PROXY_FILE;	
			
			searchBuilder.append("proxyId=" + branchCode).append(";")
			             .append("proxyName=" + branchName).append(";");
	        searchBuilder.append("feeDate=" + feeDatePair[0]).append(";")
	                     .append("_feeDate=" + feeDatePair[1]).append(";");
		} else {
			return new String[] {};
		}
		
		/**
		 * @description:<li>增加制表日期</li>
		 */
		searchBuilder.append("printDate=" + DateUtil.getCurrentDate());
		
		return new String[] {reportFile,searchBuilder.toString()};
	}
	
	/**
	 * 
	  * @description：如果是发卡机构或者是售卡代理
	  * @param roleType
	  * @param reportType
	  * @param params
	  * @return
	  * @throws Exception  
	  * @version: 2010-12-5 上午10:25:46
	  * @See:
	 */
	private String[] getLowLevelQueryParams(String roleType, String reportType, String[] params) throws Exception {
		String reportFile = null;
			
		StringBuilder searchBuilder = new StringBuilder(100);
			
		String feeDate = params[0];
		
		String branchValue = params[1];
		
		String branchCode = branchValue.split("\\|")[0];

		String branchName = branchValue.split("\\|")[1];
		
		String[] feeDatePair = DateUtil.getReportDateMonthPare(feeDate);
		
		//String branchIdValue = params[2];
		
		//String branchId = branchIdValue.split("\\|")[0];
		
		String branchId = params[2];
		
		/*
		if(StringUtils.equals(roleType,  RoleType.CARD.getValue()) && 
				StringUtils.equals(reportType, SkflReportType.CARD_SELL.getValue())) {
			reportFile = REPORT_CARD_FILE;	
			
			searchBuilder.append("branchCode=" + branchCode).append(";")
                         .append("branchName=" + branchName).append(";");
            searchBuilder.append("feeDate=" + feeDatePair[0]).append(";")
                         .append("_feeDate=" + feeDatePair[1]).append(";");
            searchBuilder.append("proxyId=" + branchId).append(";");
			
		}*/ 
		if (StringUtils.equals(roleType, RoleType.CARD_SELL.getValue()) &&
				     StringUtils.equals(reportType, SkflReportType.SELL_CARD.getValue())) {
			reportFile = REPORT_PROXY_FILE;
			
			searchBuilder.append("proxyId=" + branchCode).append(";")
                         .append("proxyName=" + branchName).append(";");
            searchBuilder.append("feeDate=" + feeDatePair[0]).append(";")
                         .append("_feeDate=" + feeDatePair[1]).append(";");
            searchBuilder.append("orgId=" + branchId).append(";");
		} else {
			return new String[] {};
		}
		
		/**
		 * @description:<li>增加制表日期</li>
		 */
		searchBuilder.append("printDate=" + DateUtil.getCurrentDate());
		
		return new String[] {reportFile,searchBuilder.toString()};
	}
	
	/**
	 * 
	  * @description:
	  * @param branchCode
	  * @return  
	  * @version: 2010-12-6 上午10:40:04
	  * @See:
	 */
	private String getBranchName(String branchCode) {
		BranchInfo branchInfo = (BranchInfo) branchInfoDAO.findByPk(branchCode);
		
		return branchInfo.getBranchName();
	}
	
	
	private boolean checkTopLevel(String roleType) {
		return (StringUtils.equals(roleType, RoleType.CENTER.getValue()) || 
		                      StringUtils.equals(roleType, RoleType.CENTER_DEPT.getValue()) ||
		                      StringUtils.equals(roleType, RoleType.FENZHI.getValue()));
		
	}

}
