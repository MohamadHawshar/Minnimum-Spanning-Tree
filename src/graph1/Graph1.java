/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph1;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Alaa Shafshak
 */
public class Graph1 {

    class Edge implements Comparable<Edge> {

        int src, dest, weight;

        public int compareTo(Edge compareEdge) {
            return this.weight - compareEdge.weight;
        }
    };

    class subset {

        int parent, rank;
    };
    int V, E;
    static Edge edge[];

    Graph1(int v, int e) {
        V = v;
        E = e;
        edge = new Edge[E];
        for (int i = 0; i < e; ++i) {
            edge[i] = new Edge();
        }
    }

    int find(subset subsets[], int i) {
        if (subsets[i].parent != i) {
            subsets[i].parent = find(subsets, subsets[i].parent);
        }
        return subsets[i].parent;
    }

    void Union(subset subsets[], int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);
        System.out.println(" the root that contains " + x + " was " + subsets[x].parent);
        System.out.println(" the root that contains " + y + " was " + subsets[y].parent);
        if (subsets[xroot].rank < subsets[yroot].rank) {
            subsets[xroot].parent = yroot;
            System.out.println(" " + subsets[xroot].rank + " < " + subsets[yroot].rank + " : rank1 < rank2 \n the subset of rank " + subsets[xroot].rank + " becomes a child of the one of rank " + subsets[yroot].rank);
        } else if (subsets[xroot].rank > subsets[yroot].rank) {
            subsets[yroot].parent = xroot;
            System.out.println(" " + subsets[yroot].rank + " < " + subsets[xroot].rank + " : rank2 < rank1 \n the subset of rank " + subsets[yroot].rank + " becomes a child of the one of rank " + subsets[xroot].rank);
        } else {
            subsets[yroot].parent = xroot;
            System.out.println(" " + subsets[xroot].rank + " = " + subsets[yroot].rank + " : rank1 = rank2 \n we make the first subset the parent");
            subsets[xroot].rank++;
        }
//printing out the root of each subset
        System.out.println(" the root that contains " + x + " is " + subsets[x].parent);
        System.out.println(" the root that contains " + y + " is " + subsets[y].parent);
    }
//Union randomly the two subsets, despite of their ranks. we make the parent of the first subset the root.
// normal implementation of uninion

    void basicUnion(subset subsets[], int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);
        subsets[yroot].parent = xroot;
    }

    //normal implementation of find

    int basicFind(subset subsets[], int i) {
        if (subsets[i].parent == -1) {
            return i;
        }
        return find(subsets, subsets[i].parent);
    }
//main methode

    void KruskalMST() {
        Edge result[] = new Edge[V];
        int e = 0; // An index variable, used for result[]
        int i = 0; // An index variable, used for sorted edges
        int weight = 0;
        for (i = 0; i < V; ++i) {
            result[i] = new Edge();
        }
        Arrays.sort(edge);
        subset subsets[] = new subset[V];
        for (i = 0; i < V; ++i) {
            subsets[i] = new subset();
        }
        for (int v = 0; v < V; ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }
        i = 0;
//Switch case to choose between the Union by rank and Basic Union methods.
        int choice;
        do {
            System.out.println("Please choose a method : ");
            System.out.println("1 - Union by rank method ");
            System.out.println("2 - Basic union method ");
            Scanner in = new Scanner(System.in);
            choice = in.nextInt();

            //printing the sorted array
            System.out.println("-----------------------------------------------");
            System.out.println("Source Destination Weight");
            for (int z = 0; z < E; z++) {
                System.out.println(edge[z].src + "        " + edge[i].dest + "           " + edge[z].weight);
            }
            System.out.println("-----------------------------------------------");
            switch (choice) {
                case 1:
                    while (e < V - 1) {
                        Edge next_edge = new Edge();
                        next_edge = edge[i++];
                        int x = find(subsets, next_edge.src);
                        int y = find(subsets, next_edge.dest);
                        if (x != y) {
                            result[e++] = next_edge;
                            //adding the weight of each edge to the variable "weight"
                            weight += next_edge.weight;
                            System.out.println(">" + next_edge.src + " -- " + next_edge.dest + " doesn't form a cycle");
                            //calling the uninon mehode
                            Union(subsets, x, y);
                            System.out.println(" putting " + next_edge.src + " and " + next_edge.dest + " in the same set");
                            //printing the resulted set
                            System.out.println(" the set " + find(subsets, x) + " now contains ");
                            System.out.print("   ");
                            for (int k = 0; k < subsets.length; k++) {
                                if (subsets[k].parent == find(subsets, x)) {
                                    System.out.print(k + " ");
                                }

                            }
                            System.out.println("");
                        } else {
                            System.out.println(">" +next_edge.src + " -- " + next_edge.dest + " forms a cycle");

                            System.out.println(" " + next_edge.src + " and " + next_edge.dest + " are ALREADY in the same set");
                        }
                        System.out.println("");
                    }
                    break;
                case 2:
                    while (e < V - 1) {
                         Edge next_edge = new Edge();
                        next_edge = edge[i++];
                        int x = find(subsets, next_edge.src);
                        int y = find(subsets, next_edge.dest);
                        if (x != y) {
                            result[e++] = next_edge;
                            //adding the weight of each edge to the variable "weight"
                            weight += next_edge.weight;
                            System.out.println(">" + next_edge.src + " -- " + next_edge.dest + " doesn't form a cycle");
                            //calling the uninon mehode
                            basicUnion(subsets, x, y);
                            System.out.println(" putting " + next_edge.src + " and " + next_edge.dest + " in the same set");
                            //printing the resulted set
                            System.out.println(" the set " + find(subsets, x) + " now contains ");
                            System.out.print("   ");
                            for (int k = 0; k < subsets.length; k++) {
                                if (subsets[k].parent == find(subsets, x)) {
                                    System.out.print(k + " ");
                                }

                            }
                            System.out.println("");
                        } else {
                            System.out.println(">" +next_edge.src + " -- " + next_edge.dest + " forms a cycle");

                            System.out.println(" " + next_edge.src + " and " + next_edge.dest + " are ALREADY in the same set");
                        }
                    }
                    break;
                default:
                    System.out.println("You should choose one method!");
                    break;
            }
        } while (choice < 0 || choice > 2);
        System.out.println("-----------------------------------------------");
        System.out.println();
        System.out.println("Following are the edges in "
                + "the constructed MST");
        for (i = 0; i < e; ++i) {
            System.out.println(result[i].src + " -- "
                    + result[i].dest + " == " + result[i].weight);
        }
        System.out.println();
        System.out.println("-----------------------------------------------");
//printing out the total weight of the MST
        System.out.println("MSTWeight = " + weight);
        System.out.println("-----------------------------------------------");

    }
//function to check wether there's another MST possible or not
/**
 * we cycle through the edge array until we find a repeated weight 
 * then we put the edge that have that weight and is in the MST in the back 
 * then run the code again to get the new MST
 */
    void checkAnotherMst(Edge[] result) {
        int counter = 0;
        Edge temp = new Edge();
        for (int i = 0; i < edge.length - 1; i++) {
            //compairing to see if there is a repeated weight
            if (edge[i].compareTo(edge[i + 1]) == 0) {
                //flipping the position with the edge 
                temp = edge[i];
                edge[i] = edge[i + 1];
                edge[i + 1] = temp;
                //counting possible MSTs
                counter++;
                do {
                    System.out.println("");
                    System.out.println("********************** Another MST **********************");
                    System.out.println("");
                    //calling the main methode again
                    KruskalMST();
                    counter--;
                } while (counter != 0);
            }
        }
    }

    public static void main(String[] args) {
        /* Let us create following weighted graph 
        this tree has 2 mst 
         10 
         0--------1 
         |  \     | 
         6|   5\   |15 
         |      \ | 
         2--------3 
           5       */
        int V = 4;
        int E = 5;
        Graph1 graph = new Graph1(V, E);
        Arrays.sort(graph.edge);
// add edge 0-1
        graph.edge[0].src = 0;
        graph.edge[0].dest = 1;
        graph.edge[0].weight = 10;
// add edge 0-2
        graph.edge[1].src = 0;
        graph.edge[1].dest = 2;
        graph.edge[1].weight = 6;
// add edge 0-3
        graph.edge[2].src = 0;
        graph.edge[2].dest = 3;
        graph.edge[2].weight = 5;
// add edge 1-3
        graph.edge[3].src = 1;
        graph.edge[3].dest = 3;
        graph.edge[3].weight = 15;
// add edge 2-3
        graph.edge[4].src = 2;
        graph.edge[4].dest = 3;
        graph.edge[4].weight = 5;
        /*graph.edge[5].src = 1;
         graph.edge[5].dest = 4;
         graph.edge[5].weight = 5;*/
        System.out.println();
        graph.KruskalMST();
        graph.checkAnotherMst(graph.edge);
    }
}
