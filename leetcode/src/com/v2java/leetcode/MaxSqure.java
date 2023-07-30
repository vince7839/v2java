package com.v2java.leetcode;

public class MaxSqure {

    public static void main(String[] args) {
        char[][] grid = {
                {'1', '0', '1', '0'},
                {'1', '0', '1', '1'},
                {'1', '0', '1', '1'},
                {'1', '1', '1', '1'}
        };
        int r = new MaxSqure().maximalSquare(grid);
        System.out.println(r);
    }


        public int maximalSquare(char[][] matrix) {
            int dp[][] = new int[matrix.length][matrix[0].length];

            for(int i=0;i<matrix.length;i++){
                for(int j=0;j<matrix[0].length;j++){
                    dp[i][j] = matrix[i][j] == '1'?1:0;
                }
            }

            int len = 0;
            boolean pass = false;
            do{
                len += 1;
                pass = false;
                for(int i=0;i<matrix.length;i++){
                    for(int j=0;j<matrix[0].length;j++){
                        if(dp[i][j] < len -1){
                            continue;
                        }
                        if(i+len>matrix.length||j+len>matrix[0].length){
                            continue;
                        }
                        if(verify(matrix,i,j,len)){
                            dp[i][j]= len;
                            if(pass == false){
                                pass = true;
                            }
                        }
                    }
                }

            }while(pass);
            return (len-1)*(len-1);
        }

        public boolean verify(char[][] matrix,int i,int j,int len){
            for(int k=0;k<len;k++){
                if(matrix[i+len-1][j+k] != '1'||matrix[i+k][j+len-1] !='1'){
                    return false;
                }
            }
            return true;
        }

}
