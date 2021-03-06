<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>${ACT.name}</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/datePicker/WdatePicker.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form id="searchForm" action="list.do" cssClass="validate-tip">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">卡号</td>
							<td><s:textfield name="adjAccReg.cardId" cssClass="{digitOrLetter:true}"/></td>
							<td align="right">商户编号</td>
							<td><s:textfield name="adjAccReg.merNo" cssClass="{digitOrLetter:true}"/></td>
							<td align="right">商户名称</td>
							<td><s:textfield name="adjAccReg.merchName"/></td>	
						</tr>	
						<tr>						
							<td align="right">终端号</td>
							<td><s:textfield name="adjAccReg.termlId"/></td>
							<td align="right">状态</td>
							<td><s:select name="adjAccReg.status" list="registerStateList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select></td>
							<td align="right">标志</td>
							<td><s:select name="adjAccReg.flag" list="flagList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" ></s:select></td>
						</tr>
						</tr>	
						<tr>
							<td>&nbsp;</td>
							<td height="30" colspan="5">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:30px;" type="button" value="清除" onclick="FormUtils.reset('searchForm')" name="escape" />
								<f:pspan pid="adjAccReg_add"><input style="margin-left:30px;" type="button" value="单笔调账退货" onclick="javascript:gotoUrl('/adjAccReg/showAdd.do');" id="input_btn3"  name="escape" /></f:pspan>
								<f:pspan pid="adjAccReg_add"><input style="margin-left:30px;" type="button" value="批量调账退货" onclick="javascript:gotoUrl('/adjAccReg/showAddBat.do');" id="input_btn3"  name="escape" /></f:pspan>
								<f:pspan pid="adjAccReg_add"><input style="margin-left:30px;" type="button" value="上传文件" onclick="javascript:gotoUrl('/adjAccReg/showFileAdjAccReg.do');" id="input_btn3"  name="escape" /></f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_ADJACCREG_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">交易流水</td>
			   <td align="center" nowrap class="titlebg">卡号</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">发起方</td>
			   <td align="center" nowrap class="titlebg">商户</td>
			   <td align="center" nowrap class="titlebg">终端编号</td>
			   <td align="center" nowrap class="titlebg">调账金额</td>
			   <td align="center" nowrap class="titlebg">原交易类型</td>
			   <td align="center" nowrap class="titlebg">标志</td>
			   <td align="center" nowrap class="titlebg">状态</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data"> 
			<tr>
			  <td align="left" nowrap>${transSn}</td>
			  <td align="center" nowrap>${cardId}</td>
			  <td align="center" nowrap>${cardBranch}</td>
			  <td align="center" nowrap>${initiator}</td>
			  <td align="left" nowrap>${merNo}-${merchName}</td>
			  <td align="center" nowrap>${termlId}</td>
			  <td align="right" nowrap>${fn:amount(amt)}</td>
			  <td align="center" nowrap>${transTypeName}</td>
			  <td align="center" nowrap>${flagName}</td>
			  <td align="center" nowrap>${statusName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/adjAccReg/detail.do?adjAccReg.adjAccId=${adjAccId}">查看</f:link>
			  	</span>
			  </td>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>