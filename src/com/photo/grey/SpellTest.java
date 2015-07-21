package com.photo.grey;

import org.xeustechnologies.googleapi.spelling.SpellChecker;
import org.xeustechnologies.googleapi.spelling.SpellCorrection;
import org.xeustechnologies.googleapi.spelling.SpellResponse;

public class SpellTest {
	public static void main(String[] args) {
		SpellChecker checker = new SpellChecker();

		 SpellResponse spellResponse = checker.check( "helloo worlrd" );

		 for( SpellCorrection sc : spellResponse.getCorrections() )
		    System.out.println( sc.getValue() );
	}
}
