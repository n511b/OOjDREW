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

package org.ruleml.oojdrew;

import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

import javax.swing.UIManager;

import org.apache.log4j.Level;
import org.ruleml.oojdrew.parsing.RuleMLFormat;

/**
 * <p>
 * Title: OO jDREW
 * </p>
 * 
 * <p>
 * Description: Reasoning Engine for the Semantic Web - Supporting basic OO RuleML 0.88 - 1.0
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005-2011
 * </p>
 * 
 */
public class Config implements Configuration {
    /**
     * This variable specifies the default output format that is produced when
     * toString() calls are made on Term and DefiniteClause Objects. If this is
     * true then output is in POSL syntax, otherwise RuleML syntax is produced.
     */
    public static boolean PRPRINT = false;

    /**
     * This variable controls how Variables are printed. If true then the
     * variable name is combined with the variable id in the output, otherwise
     * only the variable name is printed. This is useful as after unification it
     * is possible to have two different variable with the same base name, but
     * the option to disable is there so that the parsing and output libraries
     * can be used to translate from POSL syntax to RuleML 0.88 syntax without
     * changing the variable names.
     */
    public static boolean PRINTVARID = true;

    /**
     * This variable controls how generated symbols (skolem constants) are
     * printed. If this is set to true, then the generated symbol is output as a
     * regular constant (eg. <Ind>$gensym10000</Ind> or $gensym10000; in RuleML
     * and POSL respectively). If this is set to false then the generated symbol
     * is output the same as it was input (e.g. <Skolem /> or _; in RuleML and
     * POSL respectively).
     */
    public static boolean PRINTGENSYMS = false;

    /**
     * This variable controls how system generated oids are printed. If this is
     * set to true, then the generated symbol is output; with the generated
     * symbol printed as determined by PRINTGENSYMS. If it is set to false then
     * generated oids are completely omitted. This variable does not effect the
     * RuleML output, only POSL; in RuleML the oids are always printed, but
     * generated oids are printed based upon the PRINTGENSYMS variable.
     */
    public static boolean PRINTGENOIDS = true;

    /**
     * This variable controls how anonymous variables are printed. If this is
     * set to false (the default), then anonymous variables are printed as they
     * are input (<Var /> or ?; for RuleML and POSL respectively). If it is set
     * to true then the generated variable name is output (e.g.
     * <Var>$anonvar1</Var> or ?$ANON1; for RuleML and POSL respectively).
     */
    public static boolean PRINTANONVARNAMES = false;

    private Preferences preferences;
    
    private int uiPreferenceChanges;

    public Config(Class clazz) {
        preferences = Preferences.userNodeForPackage(clazz);
        uiPreferenceChanges = 1;
    }
    
    public void addPreferenceChangeListener(PreferenceChangeListener listener) {
        preferences.addPreferenceChangeListener(listener);
    }
    
    public int getUiPreferenceChangeCount() {
        return uiPreferenceChanges;
    }
    
    public void decreaseUiPreferenceChangeCount() {
        uiPreferenceChanges--;
    }

    public int getTextAreaFontSize() {
        return preferences.getInt("TextAreaFontSize", 12);
    }

    public void setTextAreaFontSize(int newSize) {
        int oldSize = getTextAreaFontSize();
        if (oldSize != newSize) {
            preferences.putInt("TextAreaFontSize", newSize);
            uiPreferenceChanges++;
        }
    }

    public int getUIFontSize() {
        return preferences.getInt("UIFontSize", 12);
    }

    public void setUIFontSize(int newSize) {
        int oldSize = getUIFontSize();
        if (oldSize != newSize) {
            preferences.putInt("UIFontSize", newSize);
            uiPreferenceChanges++;
        }
    }

    public boolean getDebugConsoleVisible() {
        return preferences.getBoolean("DebugConsoleVisible", false);
    }

    public void setDebugConsoleVisible(boolean visible) {
        if (getDebugConsoleVisible() != visible) {
            preferences.putBoolean("DebugConsoleVisible", visible);
        }
    }

    public String getLookAndFeel() {
        String defaultLookAndFeelName = UIManager.getSystemLookAndFeelClassName();
        return preferences.get("LookAndFeel", defaultLookAndFeelName);
    }

    public void setLookAndFeel(String lafClassName) {
        if (!getLookAndFeel().equals(lafClassName)) {
            preferences.put("LookAndFeel", lafClassName);
            uiPreferenceChanges++;
        }
    }

    public RuleMLFormat getRuleMLFormat() {
        String defaultRuleMLFormat = RuleMLFormat.RuleML100.getVersionName();
        String configuredRuleMLVersion = preferences.get("RuleMLFormat", defaultRuleMLFormat);
        return RuleMLFormat.fromString(configuredRuleMLVersion);
    }

    public void setSelectedRuleMLFormat(RuleMLFormat rmlFormat) {
        if (getRuleMLFormat() != rmlFormat) {
            preferences.put("RuleMLFormat", rmlFormat.getVersionName());
        }
    }

    public boolean getLinkFontSizes() {
        return preferences.getBoolean("LinkFontSizes", false);
    }

    public void setLinkFontSizes(boolean linkFontSizes) {
        if (getLinkFontSizes() != linkFontSizes) {
            preferences.putBoolean("LinkFontSizes", linkFontSizes);
        }
    }

    public Level getLogLevel() {
        Level defaultLogLevel = Level.ERROR;
        int logLevel = preferences.getInt("LogLevel", defaultLogLevel.toInt());
        return Level.toLevel(logLevel);
    }

    public void setLogLevel(Level logLevel) {
        if (getLogLevel() != logLevel) {
            preferences.putInt("LogLevel", logLevel.toInt());
        }
    }

    public int getHttpConnectionTimeout() {
        int defaultTimeoutInMilliseconds = 7000;
        return preferences.getInt("HttpConnectionTimeout", defaultTimeoutInMilliseconds);
    }

    public void setHttpConnectionTimeout(int timeoutInMilliseconds) {
        if (getHttpConnectionTimeout() != timeoutInMilliseconds) {
            preferences.putInt("HttpConnectionTimeout", timeoutInMilliseconds);
        }
    }
}