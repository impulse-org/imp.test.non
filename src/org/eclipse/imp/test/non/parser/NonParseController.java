package org.eclipse.imp.test.non.parser;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.parser.ISourcePositionLocator;
import org.eclipse.imp.parser.MessageHandlerAdapter;
import org.eclipse.imp.parser.SimpleLPGParseController;
import org.eclipse.imp.services.ILanguageSyntaxProperties;
import org.eclipse.imp.test.non.Activator;

/**
 */
public class NonParseController extends SimpleLPGParseController implements IParseController {
    public NonParseController() {
        super(Activator.kLanguageID);
    }

    public ISourcePositionLocator getSourcePositionLocator() {
        return new NonASTNodeLocator();
    }

    public ILanguageSyntaxProperties getSyntaxProperties() {
        return null;
    }

    /**
    * setFilePath() should be called before calling this method.
    */
    public Object parse(String contents, IProgressMonitor monitor) {
        PMMonitor my_monitor= new PMMonitor(monitor);
        char[] contentsArray= contents.toCharArray();

        if (fLexer == null) {
            fLexer= new NonLexer();
        }
        fLexer.reset(contentsArray, fFilePath.toPortableString());

        if (fParser == null) {
            fParser= new NonParser(fLexer.getILexStream());
        }
        fParser.reset(fLexer.getILexStream());
        fParser.getIPrsStream().setMessageHandler(new MessageHandlerAdapter(handler));

        fLexer.lexer(my_monitor, fParser.getIPrsStream()); // Lex the stream to
        // produce the token
        // stream
        if (my_monitor.isCancelled())
            return fCurrentAst; // TODO fCurrentAst might (probably will) be
        // inconsistent wrt the lex stream now

        fCurrentAst= fParser.parser(my_monitor, 0);

        cacheKeywordsOnce();

        Object result= fCurrentAst;
        return result;
    }
}
