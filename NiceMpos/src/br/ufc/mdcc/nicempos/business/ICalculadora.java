package br.ufc.mdcc.nicempos.business;

import br.ufc.mdcc.mpos.offload.Remotable;
import br.ufc.mdcc.mpos.offload.Remotable.Offload;
import br.ufc.mdcc.nicempos.model.vo.Input;

public interface ICalculadora {
	@Remotable(Offload.STATIC)
	public int calculatesClassificationPlace(Input input);
	
	public int chosenAnswerTwo(Input input);
	
	//@Remotable(Offload.STATIC)
	public int chosenAnswerThree(Input input);
	
	public int chosenAnswerFour(Input input);
	
	public int chosenAnswerFive(Input input);

}
