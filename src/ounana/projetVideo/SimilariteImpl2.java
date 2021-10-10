package ounana.projetVideo;
public class SimilariteImpl2 implements ISimilarite{

	public String titre1;
	public String titre2;
	
	public SousTitrageVideo video;
	public Double similarite;
	
	/*Distance de Levenshtein besed program*/

	
	public SimilariteImpl2(String titre1,String titre2) {
		this.titre1 = titre1;
		this.titre2 = titre2;
	}
	
	

	public SimilariteImpl2(SousTitrageVideo video, Double similarite) {
		super();
		this.video = video;
		this.similarite = similarite;
	}



	@Override
    public double calculerSimilarite(){	
    	String longer = this.titre1, shorter = this.titre2;
        if (this.titre1.length() < this.titre2.length()) { // longer should always have greater length
          longer = this.titre2; shorter = this.titre1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0; /* both strings are zero length */ }
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

      }
    
    public static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
        	System.out.println(".");
          int lastValue = i;
          for (int j = 0; j <= s2.length(); j++) {
            if (i == 0)
              costs[j] = j;
            else {
              if (j > 0) {
                int newValue = costs[j - 1];
                if (s1.charAt(i - 1) != s2.charAt(j - 1))
                  newValue = Math.min(Math.min(newValue, lastValue),
                      costs[j]) + 1;
                costs[j - 1] = lastValue;
                lastValue = newValue;
              }
            }
          }
          if (i > 0)
            costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
      }

	public String getTitre1() {
		return titre1;
	}

	public void setTitre1(String titre1) {
		this.titre1 = titre1;
	}

	public String getTitre2() {
		return titre2;
	}

	public void setTitre2(String titre2) {
		this.titre2 = titre2;
	}

	public Double getSimilarite() {
		return similarite;
	}

	public void setSimilarite(Double similarite) {
		this.similarite = similarite;
	}
    
    
    
}
