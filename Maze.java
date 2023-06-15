package ex1a; 

import java.util.*; 

public class Maze{ 
    Map<String,List<String>> map=Map.of(                //迷路の生成
        "A",List.of("B","C","D"), 
        "B",List.of("E","F"), 
        "C",List.of("G","H"), 
        "D",List.of("I","J")); 
        String root(){ 
            return"A"; 
        } 
         
        String goal(){ 
            return"E"; 
        } 
         
        public static void main(String[] args){ 
            var maze=new Maze();                        //迷路の生成 
            maze.solve();                               //迷路を解く
        } 
         
        public void solve(){ 
            var root=root();                            //スタート生成
            var goal=search(root);                      //searchで解く
            if(goal!=null)System.out.println("found");  //見つかったとき
        } 
         
        String search(String root){ 
            List<String> openList=new ArrayList<>(); 
            openList.add(root);                         //スタートを追加
             
            while(openList.size()>0){ 
                var state=get(openList);                //現在の場所
                System.out.println(state);
                if(isGoal(state)) return state;         //ゴール判定
                var children=children(state);           //子を列挙
                openList=concat(openList,children);     //子をその親代の一番最後に格納し、横型探索させる
            } 
             
            return null; 
        } 
         
        boolean isGoal(String state){ 
            return goal().equals(state);                //ゴールの判定
        } 
         
        String get(List<String> list){                  //現在の場所を得る 
            return list.remove(0); 
        } 
         
        List<String> children(String current){          //引数の子を返す
            return this.map.getOrDefault(current,Collections.emptyList()); 
        } 
         
        List<String> concat(List<String>frontList,List<String>backList){ 
            List<String> list=new ArrayList<>();        //第一引数の要素を先に持ってきて、第二引数のものを後に格納する
            list.addAll(frontList); 
            list.addAll(backList); 
            return list; 
        } 
    }

    /*
     * 1 検査isGoal
     *   選択concat
     *   展開children
     *   生成Maze
     * 
     * 2 横型探索 関数contactの処理
     * 
     * 3 contactにて引数を反対にし、見つかった子ノードから処理を行う
     * 
     * 
     *作業時間 一時間程度
     */

    //変更点

