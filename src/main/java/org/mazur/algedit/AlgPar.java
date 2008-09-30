package org.mazur.algedit;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Roman Mazur (IO-52)
 */
public class AlgPar {

  /**
   * @param args
   */
  public static void main(final String[] args) throws IOException {
    String s = "";
    s += "B ";
    s += "E ";
    s += "x X ";
    s += "y Y ";
    s += "^ ";
    s += "_ ";
    for (int i = 0; i < 10; i++) { s += i + " "; }
    s += "#10 ";
    s += "#13 ";
    s += "#32 ";
    s += "\n";
    
    // state #0
    s += "1:addBegin "; // B
    s += "11:error "; // E
    s += "11:error 11:error "; // x X
    s += "11:error 11:error "; // y Y
    s += "11:error "; // ^
    s += "11:error "; // _
    for (int i = 0; i < 10; i++) { s += "11:error "; }
    s += "0:skip "; // \n
    s += "0:skip "; // \r
    s += "0:skip "; // space
    s += "11:error"; //else
    s += "\n";

    // state #1
    s += "11:error "; // B
    s += "6:addEnd "; // E
    s += "2:startCondition 2:startCondition "; // x X
    s += "4:startOperator 4:startOperator "; // y Y
    s += "11:error "; // ^
    s += "9:startLinking "; // _
    for (int i = 0; i < 10; i++) { s += "11:error "; }
    s += "1:skip "; // \n
    s += "1:skip "; // \r
    s += "1:skip "; // space
    s += "11:error"; //else
    s += "\n";
    
    // state #2
    s += "11:error "; // B
    s += "11:error "; // E
    s += "11:error 11:error "; // x X
    s += "11:error 11:error "; // y Y
    s += "11:error "; // ^
    s += "11:error "; // _
    for (int i = 0; i < 10; i++) { s += "3:character "; }
    s += "11:error "; // \n
    s += "11:error "; // \r
    s += "11:error "; // space
    s += "11:error"; //else
    s += "\n";
    
    // state #3
    s += "11:error "; // B
    s += "6:endConditionAddEnd "; // E
    s += "11:error 11:error "; // x X
    s += "11:error 11:error "; // y Y
    s += "7:endConditionStartLink "; // ^
    s += "11:error "; // _
    for (int i = 0; i < 10; i++) { s += "3:character "; }
    s += "12:endCondition "; // \n
    s += "12:endCondition "; // \r
    s += "12:endCondition "; // space
    s += "11:error"; //else
    s += "\n";

    // state #4
    s += "11:error "; // B
    s += "11:error "; // E
    s += "11:error 11:error "; // x X
    s += "11:error 11:error "; // y Y
    s += "11:error "; // ^
    s += "11:error "; // _
    for (int i = 0; i < 10; i++) { s += "5:character "; }
    s += "11:error "; // \n
    s += "11:error "; // \r
    s += "11:error "; // space
    s += "11:error"; //else
    s += "\n";

    // state #5
    s += "11:error "; // B
    s += "6:endOperatorAddEnd "; // E
    s += "3:endOperatorStartCondition 3:endOperatorStartCondition "; // x X
    s += "4:endStartOperator 4:endStartOperator "; // y Y
    s += "7:endOperatorStartLink "; // ^
    s += "9:endOperatorStartLinking "; // _
    for (int i = 0; i < 10; i++) { s += "3:character "; }
    s += "1:endOperator "; // \n
    s += "1:endOperator "; // \r
    s += "1:endOperator "; // space
    s += "11:error"; //else
    s += "\n";

    // state #6
    s += "11:error "; // B
    s += "11:error "; // E
    s += "11:error 11:error "; // x X
    s += "11:error 11:error "; // y Y
    s += "11:error "; // ^
    s += "11:error "; // _
    for (int i = 0; i < 10; i++) { s += "11:error "; }
    s += "6:skip "; // \n
    s += "6:skip "; // \r
    s += "6:skip "; // space
    s += "11:error"; //else
    s += "\n";

    // state #7
    s += "11:error "; // B
    s += "11:error "; // E
    s += "11:error 11:error "; // x X
    s += "11:error 11:error "; // y Y
    s += "11:error "; // ^
    s += "11:error "; // _
    for (int i = 0; i < 10; i++) { s += "8:character "; }
    s += "11:error "; // \n
    s += "11:error "; // \r
    s += "11:error "; // space
    s += "11:error"; //else
    s += "\n";

    // state #8
    s += "11:error "; // B
    s += "6:endLinkAddEnd "; // E
    s += "2:endLinkStartCondition 2:endLinkStartCondition "; // x X
    s += "2:endLinkStartOperator 2:endLinkStartOperator "; // y Y
    s += "11:error "; // ^
    s += "9:endLinkStartLinking "; // _
    for (int i = 0; i < 10; i++) { s += "8:character "; }
    s += "1:endLink "; // \n
    s += "1:endLink "; // \r
    s += "1:endLink "; // space
    s += "11:error"; //else
    s += "\n";

    // state #9
    s += "11:error "; // B
    s += "11:error "; // E
    s += "11:error 11:error "; // x X
    s += "11:error 11:error "; // y Y
    s += "11:error "; // ^
    s += "11:error "; // _
    for (int i = 0; i < 10; i++) { s += "10:character "; }
    s += "11:error "; // \n
    s += "11:error "; // \r
    s += "11:error "; // space
    s += "11:error"; //else
    s += "\n";

    // state #10
    s += "11:error "; // B
    s += "6:endLinkingAddEnd "; // E
    s += "2:endLinkingStartCondition 2:endLinkingStartCondition "; // x X
    s += "4:endLinkingStartOperator 4:endLinkingStartOperator "; // y Y
    s += "11:error "; // ^
    s += "9:endStartLinking "; // _
    for (int i = 0; i < 10; i++) { s += "10:character "; }
    s += "1:endLinking "; // \n
    s += "1:endLinking "; // \r
    s += "1:endLinking "; // space
    s += "11:error"; //else
    s += "\n";

    // state #11
    s += "11:error";
    s += "\n";
    
    // state #12
    s += "11:error "; // B
    s += "11:error "; // E
    s += "11:error 11:error "; // x X
    s += "11:error 11:error "; // y Y
    s += "7:startLink "; // ^
    s += "11:error "; // _
    for (int i = 0; i < 10; i++) { s += "11:error "; }
    s += "12:skip "; // \n
    s += "12:skip "; // \r
    s += "12:skip "; // space
    s += "11:error"; //else
    s += "\n";

    FileWriter w = new FileWriter("alg.par");
    w.append(s);
    w.close();
  }

}
