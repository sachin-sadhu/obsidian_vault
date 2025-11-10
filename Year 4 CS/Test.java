import java.util.Deque;
import java.util.LinkedList;

public class Test {

    // Function that checks for global arc consistency, uses a queue 
    public static void AC3(ArcList arcs) {
        Queue<Arc> arcList = new LinkedList<>();
        boolean consistent = true;

        while (!arcList.isEmpty()) {
            Arc currentArc = arcList.dequeue();
            if (reviseArc(currentArc)) {
                for (Arc futureArc : arcList) {
                    if (futureArc.equals(currentArc)) continue;

                    if (futureArc.contains(currentArcNodes)) {
                        arcList.enqueue(currentArc);
                    }
                }
            }
        }
    }

    // Want to go through the domain of the 2 varaibles and remove values that do not have a support
    public static boolean reviseAC3(Arc arc) {
        boolean changed = false;
        x_domain = domain(arc.variable("left"));
        y_domain = domain(arc.variable("right"));

        for (int value : x_domain) {
            boolean has_support = false;
            int index = 0;
            
            while (!has_support && index < y_domain.size()) {
                if (constraint(value, y_domain.get(index))) {
                    has_support = true;
                }
                index ++;
            }

            // check if could not find a support, remove value from x_domain
            if (!has_support) {
                x_domain.remove(value);
                changed = true;
            }
        }

        return changed;
    }

    public static void forwardChecking(List<Variable> varList) {

        if (complete()) {
            System.out.println(solution);
            return;
        }

        Variable currentVar = varList.pop();
        int currentVal = selectValue(currentVar);
        branchFCLeft(varList, currentVar, currentVal);
        branchFCRight(varList, currentVar, currentVal);
    }

    public static void branchFCLeft(List<Variable> varList, Variable currentVar, int currentVal) {
        assign(currentVar, currentVal);
        if (reviseFutureArcs(arcs, currentVar)) {
            forwardChecking(varList.remove(currentVar));
        }
        undoPruning();
        unassign(currentVar, currentVal);
    }

    public static void branchFCRight(List<Variable> varList, Variable currentVar, int currentVal) {
        remove(currentVar, currentVal); // remove currentVal from currentVar's domain

        if (empty(domain(currentVar))) return;

        if (reviseFutureArcs(arcs, currentVar)) {
            forwardChecking(varList);
        }
        undoPruning();
        addValue(currentVar, currentVal);
    }

    public static boolean reviseFutureArcs(List<Variable> varList, Variable currentVar) {
        for (Variable futureVariable : varList) {
            if (futureVariable.equals(currentVar)) continue;

            if (!revise(arc(currentVar, futureVariable))) return false;
        }

        return true;

    }
    public static void main(String[] args) {
        
    }
}