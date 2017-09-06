package common.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.entities.Tip;
import common.entities.dto.TipDto;

@Service
public class TipService {

	@Autowired
	private TipRepository tipRepository;
	
	public Tip createTip(TipDto tipDto) {
		Tip createdTip = new Tip();
		createdTip.setSection(tipDto.getSection());
		createdTip.setTitle(tipDto.getTitle());
		createdTip.setContent(tipDto.getContent());
		createdTip.setHint(tipDto.getHint());

		return createdTip;
	}
	
	public Iterable<Tip> findAll(){
		return tipRepository.findAll();
	}
	
	public void save(Tip tip){
		tipRepository.save(tip);
	}
}
