// OO jDREW - An Object Oriented extension of the Java Deductive Reasoning Engine for the Web
// Copyright (C) 2005 Marcel Ball
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

package org.ruleml.oojdrew.Builtins;

import java.util.Vector;

import org.ruleml.oojdrew.util.DefiniteClause;
import org.ruleml.oojdrew.util.SymbolTable;
import org.ruleml.oojdrew.util.Term;
import org.ruleml.oojdrew.util.Types;

/**
 * Implements a Contains built-in relation.
 * 
 * Calling format contains(?input1, input2)
 * 
 * Satisfied iff the first argument contains the second argument (case sensitive).
 *
 * <p>Title: OO jDREW</p>
 *
 * <p>Description: Reasoning Engine for the Semantic Web - Supporting OO RuleML
 * 0.88</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Marcel A. Ball
 * @version 0.89
 */
public class ContainsBuiltin implements Builtin {
    private int symbol = SymbolTable.internSymbol("contains");

    public DefiniteClause buildResult(Term t) {
        if (t.getSymbol() != symbol) {
            return null;
        }

        if (t.subTerms.length != 3) {
            return null;
        }

        Term p1 = t.subTerms[1].deepCopy();
        Term p2 = t.subTerms[2].deepCopy();

        if (p1.getSymbol() < 0 || p2.getSymbol() < 0) {
            return null;
        }

        if (p1.getType() != Types.ISTRING || p2.getType() != Types.ISTRING) {
            return null;
        }

        String p1s = p1.getSymbolString();
        String p2s = p2.getSymbolString();

        if (p1s.indexOf(p2s) == -1) {
            return null;
        }

        Term roid = new Term(SymbolTable.internSymbol("$jdrew-contains-" + p1s
                                                      + "-" + p2s),
                             SymbolTable.IOID, Types.ITHING);

        Vector v = new Vector();
        v.add(roid);
        v.add(p1);
        v.add(p2);

        Term atm = new Term(symbol, SymbolTable.INOROLE, Types.IOBJECT, v);
        atm.setAtom(true);
        Vector v2 = new Vector();
        v2.add(atm);
        return new DefiniteClause(v2, new Vector());
    }

    public int getSymbol() {
        return symbol;
    }
}
