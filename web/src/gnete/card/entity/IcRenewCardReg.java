package gnete.card.entity;

import gnete.card.entity.flag.ReversalFlag;
import gnete.card.entity.flag.YesOrNoFlag;
import gnete.card.entity.state.RegisterState;
import gnete.card.entity.type.IcRenewCardType;

import java.math.BigDecimal;
import java.util.Date;

public class IcRenewCardReg {
	private String id;

	private String newCardId;

	private String oldCardId;

	private String acctId;

	private String custName;

	private String certType;

	private String certNo;

	private String renewType;

	private String status;

	private String cardBranch;

	private String appOrgId;

	private String branchCode;

	private String updateUser;

	private Date updateTime;

	private String remark;

	private String cardSn;

	private String arqc;

	private String aqdt;

	private String arpc;

	private String chkRespCode;

	private String writeRespCode;

	private String writeScript;

	private String writeCardFlag;

	private String randomSessionId;

	private String reversalFlag;
	
	private BigDecimal oldBalanceAmt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNewCardId() {
		return newCardId;
	}

	public void setNewCardId(String newCardId) {
		this.newCardId = newCardId;
	}

	public String getOldCardId() {
		return oldCardId;
	}

	public void setOldCardId(String oldCardId) {
		this.oldCardId = oldCardId;
	}

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getRenewType() {
		return renewType;
	}

	public void setRenewType(String renewType) {
		this.renewType = renewType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCardBranch() {
		return cardBranch;
	}

	public void setCardBranch(String cardBranch) {
		this.cardBranch = cardBranch;
	}

	public String getAppOrgId() {
		return appOrgId;
	}

	public void setAppOrgId(String appOrgId) {
		this.appOrgId = appOrgId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCardSn() {
		return cardSn;
	}

	public void setCardSn(String cardSn) {
		this.cardSn = cardSn;
	}

	public String getArqc() {
		return arqc;
	}

	public void setArqc(String arqc) {
		this.arqc = arqc;
	}

	public String getAqdt() {
		return aqdt;
	}

	public void setAqdt(String aqdt) {
		this.aqdt = aqdt;
	}

	public String getArpc() {
		return arpc;
	}

	public void setArpc(String arpc) {
		this.arpc = arpc;
	}

	public String getChkRespCode() {
		return chkRespCode;
	}

	public void setChkRespCode(String chkRespCode) {
		this.chkRespCode = chkRespCode;
	}

	public String getWriteRespCode() {
		return writeRespCode;
	}

	public void setWriteRespCode(String writeRespCode) {
		this.writeRespCode = writeRespCode;
	}

	public String getWriteScript() {
		return writeScript;
	}

	public void setWriteScript(String writeScript) {
		this.writeScript = writeScript;
	}

	public String getWriteCardFlag() {
		return writeCardFlag;
	}

	public void setWriteCardFlag(String writeCardFlag) {
		this.writeCardFlag = writeCardFlag;
	}

	public String getRandomSessionId() {
		return randomSessionId;
	}

	public void setRandomSessionId(String randomSessionId) {
		this.randomSessionId = randomSessionId;
	}

	public String getReversalFlag() {
		return reversalFlag;
	}

	public void setReversalFlag(String reversalFlag) {
		this.reversalFlag = reversalFlag;
	}
	
	public BigDecimal getOldBalanceAmt() {
		return oldBalanceAmt;
	}

	public void setOldBalanceAmt(BigDecimal oldBalanceAmt) {
		this.oldBalanceAmt = oldBalanceAmt;
	}

	public String getRenewTypeName() {
		return IcRenewCardType.ALL.get(this.renewType) == null ? "" : IcRenewCardType.valueOf(this.renewType).getName();
	}
	
	/**
	 * 状态名
	 * @return
	 */
	public String getStatusName() {
		return RegisterState.ALL.get(status) == null ? "" : RegisterState.valueOf(status).getName();
	}
	
	/**
	 * 是否写卡成功标志名
	 * 
	 * @return
	 */
	public String getWriteCardFlagName() {
		return YesOrNoFlag.ALL.get(this.writeCardFlag) == null ? "" : YesOrNoFlag.valueOf(this.writeCardFlag).getName();
	}

	/**
	 * 冲正标志：00-未冲正，01-已冲正，02-冲正失败
	 * 
	 * @return
	 */
	public String getReversalFlagName() {
		return ReversalFlag.ALL.get(this.reversalFlag) == null ? "" : ReversalFlag.valueOf(this.reversalFlag).getName();
	}
}