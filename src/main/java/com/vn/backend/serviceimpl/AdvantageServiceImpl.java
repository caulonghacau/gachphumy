package com.vn.backend.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vn.backend.dto.AdvantageDto;
import com.vn.backend.model.Advantage;
import com.vn.backend.repository.AdvantageRepository;
import com.vn.backend.service.AdvantageService;

@Service
public class AdvantageServiceImpl implements AdvantageService {

	@Autowired
	private AdvantageRepository advantageRepository;

	@Override
	public List<AdvantageDto> getListAdvantage() {

		List<Advantage> advantages = advantageRepository.findAll();
		List<AdvantageDto> results = new ArrayList<>();
		for (Advantage advantage : advantages) {
			AdvantageDto dto = new AdvantageDto();
			BeanUtils.copyProperties(advantage, dto);
			results.add(dto);

		}
		return results;
	}

}
