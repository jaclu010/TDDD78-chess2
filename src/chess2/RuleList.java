package chess2;

import java.util.ArrayList;
import java.util.List;

public class RuleList
{
    private static RuleList INSTANCE = new RuleList();
    private final List<Rule> ruleList;

    public static RuleList getInstance() {
	return INSTANCE;
    }

    private RuleList() {
        ruleList = new ArrayList<Rule>();
        RuleFactory.getRules();
    }

    public List<Rule> getRules(String piece){

    }
}
