package org.eclipse.imp.test.non.parser;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.imp.services.ILanguageSyntaxProperties;
import org.eclipse.imp.model.ISourceProject;
import org.eclipse.imp.parser.ILexer;
import org.eclipse.imp.parser.IMessageHandler;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.ISourcePositionLocator;
import org.eclipse.imp.parser.MessageHandlerAdapter;
import org.eclipse.imp.parser.SimpleLPGParseController;

import org.eclipse.imp.test.non.Activator;

/**
 */
public class NonParseController extends SimpleLPGParseController implements IParseController {
    private NonParser parser;

    private NonLexer lexer;

    public NonParseController() {
        super(Activator.kLanguageID);
    }

    /**
     * @param filePath
     *            Absolute path of file
     * @param project
     *            Project that contains the file
     * @param handler
     *            A message handler to receive error messages (or any others)
     *            from the parser
     */
    public void initialize(IPath filePath, ISourceProject project, IMessageHandler handler) {
        super.initialize(filePath, project, handler);
    }

    public IParser getParser() {
        return parser;
    }

    public ILexer getLexer() {
        return lexer;
    }

    public ISourcePositionLocator getNodeLocator() {
        return new NonASTNodeLocator();
    }

    public ILanguageSyntaxProperties getSyntaxProperties() {
        return null;
    }

    /**
    * setFilePath() should be called before calling this method.
    */
    public Object parse(String contents, boolean scanOnly, IProgressMonitor monitor) {
        PMMonitor my_monitor= new PMMonitor(monitor);
        char[] contentsArray= contents.toCharArray();

        if (lexer == null) {
            lexer= new NonLexer();
        }
        lexer.reset(contentsArray, fFilePath.toPortableString());

        if (parser == null) {
            parser= new NonParser(lexer.getLexStream());
        }
        parser.reset(lexer.getLexStream());
        parser.getParseStream().setMessageHandler(new MessageHandlerAdapter(handler));

        lexer.lexer(my_monitor, parser.getParseStream()); // Lex the stream to
        // produce the token
        // stream
        if (my_monitor.isCancelled())
            return fCurrentAst; // TODO fCurrentAst might (probably will) be
        // inconsistent wrt the lex stream now

        fCurrentAst= parser.parser(my_monitor, 0);

        cacheKeywordsOnce();

        Object result= fCurrentAst;
        return result;
    }
}
