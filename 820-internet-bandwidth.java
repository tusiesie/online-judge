// https://onlinejudge.org/index.php?option=onlinejudge&Itemid=8&page=show_problem&problem=761

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int networkNum = 1;
        StringBuilder output = new StringBuilder();

        while (true) {

            int n = scanner.nextInt();
            if (n == 0) break; // End of input

            int source = scanner.nextInt() - 1;
            int sink = scanner.nextInt() - 1;
            int connection = scanner.nextInt();

            int[][] capacity = new int[n][n];

            for (int i = 0; i < connection; i++) {

                int node1 = scanner.nextInt() - 1; // index of node1
                int node2 = scanner.nextInt() - 1; // index of node2
                int bandwidth = scanner.nextInt();
                capacity[node1][node2] += bandwidth;
                capacity[node2][node1] += bandwidth;
            }

            int maxBandwidth = fordFulkerson(capacity, source, sink, n);

            output.append("Network ").append(networkNum).append("\n");
            output.append("The bandwidth is ").append(maxBandwidth).append(".\n\n");
            networkNum++;

        }

        System.out.print(output.toString());
        scanner.close();
    }


    public static int fordFulkerson(int[][] capacity, int source, int sink, int n) {

        int[] parent = new int[n];
        int flow = 0;

        while (bfs(capacity, source, sink, parent, n)) {

            int path_flow = Integer.MAX_VALUE; // infinity

            for (int node1 = sink; node1 != source; node1 = parent[node1]) {

                int node2 = parent[node1];
                path_flow = Math.min(path_flow, capacity[node2][node1]);

            }

            for (int node3 = sink; node3 != source; node3 = parent[node3]) {

                int node4 = parent[node3];
                capacity[node4][node3] -= path_flow;
                capacity[node3][node4] += path_flow;

            }

            flow += path_flow;

        }

        return flow;

    }


    public static boolean bfs(int[][] capacity, int source, int sink, int[] parent, int n) {

        boolean[] visited = new boolean[n];
        ArrayDeque<Integer> pending = new ArrayDeque<Integer>();

        pending.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!pending.isEmpty()) {

            int cur = pending.poll();

            for (int node = 0; node < n; node++) {
                if (!visited[node] && capacity[cur][node] > 0) {
                    pending.add(node);
                    parent[node] = cur;
                    visited[node] = true;
                }
            }
        }

        return visited[sink];

    }

}
