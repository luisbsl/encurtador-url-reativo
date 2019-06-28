package br.com.encurtadorurlreativo.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

public class TempoUtilTest {
	
	@Test
	public void obterEmMilisegundosTempoDeExpiracaoEm24h_deveSerMenorQueTempoObtidoAposAlgunsMilisegundos() {
		Long daquiA24hComDelayEmMilisegundos = TempoUtil.obterPrazoDeExpiracaoDeURLEncurtadaDaquiA24H();
		assertTrue(daquiA24hComDelayEmMilisegundos.compareTo(Instant.now().plus(24, ChronoUnit.HOURS).toEpochMilli())==-1);
	}
	
	@Test
	public void verificarSeOTempoEstaVencido_deveRetornarFalseEmComparacaoA24hDepois() {
		Long daquiA24h = Instant.now().plus(24, ChronoUnit.HOURS).toEpochMilli();
		assertFalse(TempoUtil.oPrazoDaURLEncurtadaExpirou(daquiA24h));
	}

}
