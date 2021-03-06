<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>${ACT.name}</title>
		
		<f:css href="/css/page.css"/>
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
			$(function(){
				Selector.selectArea('idArea_sel', 'idArea', true);
			});

			function doConfirm(){
				var bankNo = $('#bankNo').val();
				var bankType = $('#bankType').val();
				var idArea = $('#idArea').val();
				hideMsg();
				if(bankNo.substring(0,3)!=bankType){
					showMsg("银行代码前3位必须是银行行别号！");
					return false;
				}else if(bankNo.substring(3,7)!=idArea){
					showMsg("银行代码第4位到第7位必须是地区码！");
					return false;
				}
				return true;
			}
			
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						
						<tr>
							<td width="80" height="30" align="right">银行行号</td>
							<td>
								<s:textfield id="bankNo" name="bankInfo.bankNo" maxlength="12" cssClass="{required:true , num:true, minlength:12} "/>
								<span class="field_tipinfo">输入12位数字</span>
							</td>
							<td width="80" height="30" align="right">银行名称</td>
							<td>
								<s:textfield name="bankInfo.bankName" cssClass="{required:true}" />
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">银行行别</td>
							<td>
								<s:select id="bankType" name="bankInfo.bankType" list="bankTypeList" headerKey="" headerValue="--请选择--" listKey="bankType" listValue="bankTypeName" cssClass="{required:true}"/>
							</td>
							<td width="80" height="30" align="right">地区</td>
							<td>
								<s:hidden id="idArea" name="bankInfo.addrNo" />
								<s:textfield id="idArea_sel" name="areaName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择地区</span>
							</td>
						</tr>
						
						<tr>	
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok"  onclick="return doConfirm();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/bankInfo/list.do')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_BANKMGR_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>