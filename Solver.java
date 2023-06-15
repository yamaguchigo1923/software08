package ex1b;

import java.util.*;

interface Action {
}

interface World extends Cloneable {
    boolean isGoal();
    List<Action> actions();
    World perform(Action action);
}

class State {                               //迷路状態の生成
    State parent;
    World world;

    State(World initialWorld) {             //根
        this.parent = null;
        this.world = initialWorld;
    }

    State(State parent, World child) {      //葉
        this.parent = parent;
        this.world = child;
    }

    public String toString() {
        return this.world.toString();
    }
}

public class Solver {
    World world;

    public void solve(World world) {         //根から関数を呼び出してゴールを求め、出力する。
        this.world = world;
        var root = new State(this.world);
        var goal = search(root);
        if(goal != null) printSolution(goal);
    }

    State search(State root) {
        List<State> openList = new ArrayList<>();
        openList.add(root);                 //スタートを格納

        while (openList.size() > 0) {
            var state = get(openList);
            if(isGoal(state)) return state; //ゴールが見つかったら、結果を返却する
            var children = children(state);
            openList = contact(openList, children); //子ノードを発見し、前回と同様に後に加える
        }

        return null;
    }

    boolean isGoal(State state){            //ゴール判定
        return state.world.isGoal();
    }

    State get(List<State> list) {           //先頭の要素を取り出して削除
        return list.remove(0);              
    }

    List<State> children(State state) {     //actions,performを使って、子ノードを発見して返す
        List<State> children = new ArrayList<>();
        for(var action : state.world.actions()) {       //子ノード分 繰り返し
            var next = state.world.perform(action);     //子ノードの判定
            if(next != null) {
                var child = new State(state, next);
                children.add(child);                    //リストに追加して返却
            }
        }
        return children;
    }

    List<State> contact(List<State> frontList, List<State> backList) {
        List<State> list = new ArrayList<>();
        list.addAll(frontList);
        list.addAll(backList);
        return list;                        //前の課題と同様に、リストに要素を格納する関数
    }

    void printSolution(State goal) {        //ゴールから、親をたどることで結果を出力する
        while(goal != null) {               
            System.out.print(goal + " <- ");
            goal = goal.parent;             //親をたどる
        }
        System.out.println("start");        //たどれなくなったら終了
    }
}    