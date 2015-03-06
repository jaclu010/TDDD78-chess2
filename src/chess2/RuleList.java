package chess2;

import java.util.ArrayList;
import java.util.List;

public class RuleList
{
    private static RuleList INSTANCE = new RuleList();
    private final List<Rule> ruleList;

    private RuleList() {
        ruleList = RuleFactory.getRules();
    }

    public List<Rule> extractRulesFor(String piece){
        List<Rule> pieceRules = new ArrayList<Rule>();
        for (Rule rule : ruleList) {
            if ((rule.getPiece()).equals(piece)){
                pieceRules.add(rule);
            }
        }
        return pieceRules;
    }

    public List<Rule> extractRulesForPawn(Boolean player){
        List<Rule> pieceRules = new ArrayList<Rule>();
        for (Rule rule : ruleList) {
            if (rule.getPlayer() != null &&(rule.getPlayer()).equals(player)){
                pieceRules.add(rule);
            }
        }
        return pieceRules;
    }

    public static RuleList getInstance() {
	return INSTANCE;
    }
}
