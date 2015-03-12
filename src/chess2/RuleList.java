package chess2;

import java.util.ArrayList;
import java.util.List;

public final class RuleList
{
    private static final RuleList INSTANCE = new RuleList();
    private final List<Rule> ruleList;

    private RuleList() {
        ruleList = RuleFactory.getRules();
    }

    public List<Rule> extractRulesFor(PieceType pT){
        List<Rule> pieceRules = new ArrayList<>();
        for (Rule rule : ruleList){
            if (rule.getPieceType() == pT){
                pieceRules.add(rule);
            }
        }
        return pieceRules;
    }

    public static RuleList getInstance() {
	return INSTANCE;
    }
}
