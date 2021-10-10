package ounana.projetVideo.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import ounana.projetVideo.SousTitrageVideo;

class SousTitrageVideoTest {

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void SousTitrageVideoFormatTestOK() {
		
		SousTitrageVideo stv = new SousTitrageVideo("nomdufichier.srt");
		Assert.assertEquals(stv.getFormat(), "srt");
		
	}

}
