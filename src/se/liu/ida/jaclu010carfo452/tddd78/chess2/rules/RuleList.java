package se.liu.ida.jaclu010carfo452.tddd78.chess2.rules;

import se.liu.ida.jaclu010carfo452.tddd78.chess2.PieceType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Singleton/Factory class for the rules of movement
 * @author jaclu010, carfo452
 */
public final class RuleList
{
    private static final RuleList INSTANCE = new RuleList();
        private final List<Rule> ruleList;

        private RuleList() {
            ruleList = RuleCreator.getRules();
        }

        public static RuleList getInstance() {
    	return INSTANCE;
        }

        public Iterable<Rule> extractRulesFor(PieceType pT){
            List<Rule> pieceRules = ruleList.stream().filter(rule -> rule.getpT() == pT).collect(Collectors.toList());
            return pieceRules;
        }
}
