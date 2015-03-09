package chess2;

import java.util.List;
import java.util.stream.Collectors;

public class RuleList
{
    private static RuleList INSTANCE = new RuleList();
    private final List<Rule> ruleList;

    private RuleList() {
        ruleList = RuleFactory.getRules();
    }

    public List<Rule> extractRulesFor(PieceType pT){
        List<Rule> pieceRules = ruleList.stream().filter(rule -> rule.getPieceType() == pT).collect(Collectors.toList());
        return pieceRules;
    }

    public List<Rule> extractRulesForPawn(Boolean player){
        List<Rule> pieceRules = ruleList.stream().filter(rule -> rule.getPlayer() != null && (rule.getPlayer()).equals(player))
                .collect(Collectors.toList());
        return pieceRules;
    }

    public static RuleList getInstance() {
	return INSTANCE;
    }
}
