package de.schmiereck.qwaves;

public class Rule {
    public interface RuleFunctionInterface {
        void execute(final Wave wave, final Cell cell, final Cell[] cellArr);
    }
    private RuleFunctionInterface ruleFunction;

    public Rule(final RuleFunctionInterface ruleFunction) {
        this.ruleFunction = ruleFunction;
    }
    public void execute(final Wave wave, final Cell cell, final Cell[] cellArr) {
        this.ruleFunction.execute(wave, cell, cellArr);
    }
}
