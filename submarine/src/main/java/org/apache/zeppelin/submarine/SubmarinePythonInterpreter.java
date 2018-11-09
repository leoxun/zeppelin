package org.apache.zeppelin.submarine;

import org.apache.zeppelin.interpreter.InterpreterContext;
import org.apache.zeppelin.interpreter.InterpreterException;
import org.apache.zeppelin.interpreter.InterpreterResult;
import org.apache.zeppelin.python.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class SubmarinePythonInterpreter extends PythonInterpreter {
  private static final Logger LOG = LoggerFactory.getLogger(SubmarinePythonInterpreter.class);

  public final String REPL_NAME = "sumbarine.python";
  private SubmarineInterpreter submarineInterpreter = null;
  private SubmarineContext submarineContext = null;

  public SubmarinePythonInterpreter(Properties property) {
    super(property);
  }

  @Override
  public void open() throws InterpreterException {
    super.open();
    submarineContext = SubmarineContext.getInstance(properties);
    submarineInterpreter = getInterpreterInTheSameSessionByClassName(
        SubmarineInterpreter.class);
    submarineInterpreter.setPythonWorkDir(getPythonWorkDir());
  }

  @Override
  public InterpreterResult interpret(String st, InterpreterContext context)
      throws InterpreterException {

    submarineContext.saveParagraphToFiles(context.getNoteId(),
        context.getNoteName(), getPythonWorkDir().getAbsolutePath());

    return super.interpret(st, context);
  }
}
