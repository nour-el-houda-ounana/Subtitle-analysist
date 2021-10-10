package ounana.projetVideo;

public class SousTitrageSimilarite {
	
	public SousTitrageVideo titreVideo;
	public Double similarite;
	
	public SousTitrageVideo getVideo() {
		return titreVideo;
	}
	
	public String getTitreVideo() {
		return titreVideo.getTitre();
	}
	
	public void setTitreVideo(SousTitrageVideo titreVideo) {
		this.titreVideo = titreVideo;
	}
	public Double getSimilarite() {
		return similarite;
	}
	public void setSimilarite(Double similarite) {
		this.similarite = similarite;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(Double.compare(this.getSimilarite(), ((SousTitrageSimilarite) obj).getSimilarite()) == 0 && this.getTitreVideo() == ((SousTitrageSimilarite) obj).getTitreVideo()) {
			return true;
		}else
			return false;
	}

	public String getSousTitre() {
		return titreVideo.getSousTitre();
	}
	
	
	
	
}
