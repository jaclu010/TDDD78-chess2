package se.liu.ida.jaclu010carfo452.tddd78.chess2;


import java.util.List;
import java.util.stream.Collectors;

/**
 * Singleton class for the rules of movement
 * @author jaclu010, carfo452
 */
public final class RuleList
{
    private static final RuleList INSTANCE = new RuleList();
    private final List<Rule> ruleList;

    private RuleList() {
        ruleList = RuleFactory.getRules();
    }

    public Iterable<Rule> extractRulesFor(PieceType pT){
        List<Rule> pieceRules = ruleList.stream().filter(rule -> rule.getpT() == pT).collect(Collectors.toList());
        return pieceRules;
    }

    public static RuleList getInstance() {
	return INSTANCE;
    }
}
