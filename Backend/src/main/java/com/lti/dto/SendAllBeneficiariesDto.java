package com.lti.dto;

import java.util.List;

public class SendAllBeneficiariesDto extends Status {
	private List<ViewAllBeneficiariesDto> beneficiaryDto;

	public List<ViewAllBeneficiariesDto> getBeneficiaryDto() {
		return beneficiaryDto;
	}

	public void setBeneficiaryDto(List<ViewAllBeneficiariesDto> beneficiaryDto) {
		this.beneficiaryDto = beneficiaryDto;
	}


}
