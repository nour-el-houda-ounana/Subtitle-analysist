package ounana.projetVideo;
import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;

public class SimilariteImpl1 implements ISimilarite {

	
	public String titre1;
	public String titre2;

	public SimilariteImpl1(String titre1,String titre2) {
		this.titre1 = titre1;
		this.titre2 = titre2;
	}
	
    @Override
    public double calculerSimilarite(){
    	
    	SimilarityStrategy strategy = new JaroWinklerStrategy();
		StringSimilarityService service = new StringSimilarityServiceImpl(strategy);
		double score = service.score(this.titre1, this.titre2);

    	return score;
    }
    




    
}
