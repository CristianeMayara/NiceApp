package br.ufc.mdcc.nicempos;

import br.ufc.mdcc.mpos.offload.Remotable;
import br.ufc.mdcc.mpos.offload.Remotable.Offload;

public interface ICalculadora {
	@Remotable
	public int chosenAnswerOne(Input input);
	
	public int chosenAnswerTwo(Input input);
	
	//@Remotable(Offload.STATIC)
	public int chosenAnswerThree(Input input);
	
	public int chosenAnswerFour(Input input);
	
	public int chosenAnswerFive(Input input);

}
