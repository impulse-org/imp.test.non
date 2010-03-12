package org.eclipse.imp.test.non.parser;

import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.parser.ISourcePositionLocator;
import org.eclipse.imp.parser.SimpleLPGParseController;
import org.eclipse.imp.services.ILanguageSyntaxProperties;
import org.eclipse.imp.test.non.Activator;

/**
 */
public class NonParseController extends SimpleLPGParseController implements IParseController {
    public NonParseController() {
        super(Activator.kLanguageID);
        fLexer= new NonLexer();
        fParser= new NonParser();
    }

    public ISourcePositionLocator getSourcePositionLocator() {
        return new NonASTNodeLocator();
    }

    public ILanguageSyntaxProperties getSyntaxProperties() {
        return null;
    }
}
