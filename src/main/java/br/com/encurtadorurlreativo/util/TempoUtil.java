package br.com.encurtadorurlreativo.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public final class TempoUtil {

	public static Long obterPrazoDeExpiracaoDeURLEncurtadaDaquiA24H() {
		return Instant.now().plus(24, ChronoUnit.HOURS).toEpochMilli();
	}

	public static Boolean oPrazoDaURLEncurtadaExpirou(long expiraEm) {
		return expiraEm < Instant.now().toEpochMilli();
	}

}
