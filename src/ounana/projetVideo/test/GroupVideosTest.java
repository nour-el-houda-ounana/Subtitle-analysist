package ounana.projetVideo.test;

import static org.junit.Assert.assertFalse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import ounana.projetVideo.GroupVideos;
import ounana.projetVideo.SousTitrageVideo;

class GroupVideosTest {

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSimilariteOK() {
		
		GroupVideos gv = new GroupVideos();
		SousTitrageVideo stv1 = new SousTitrageVideo("video 1.srt");
		stv1.sousTitre = "AAA BBB CCC DDD";
		
		SousTitrageVideo stv2 = new SousTitrageVideo("video 2.srt");
		stv2.sousTitre  = "AAA BBB CCC DDD";
		gv.getListeVideos().add(stv1);
		gv.getListeVideos().add(stv2);
		
		assertFalse(gv.groupVideosBySimilarite().isEmpty());

	}

}
