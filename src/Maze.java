public class Maze {
    private int[][] mazelayout;
    private int curX, curY;
    private boolean isFinished;
    private String[] dir =
            {"North", "East", "South", "West"};
    private int dirindex;
    private String curDir;

    /*public Maze()
    {
    	curX = 2;
        curY = 0;
        isFinished = false;
        dirindex = 2;
        curDir = dir[dirindex];
    }*/
    public Maze(int[][] mazelayout) {
        this.mazelayout = mazelayout;
        curX = 2;
        curY = 0;
        isFinished = false;
        dirindex = 2;
        curDir = dir[dirindex];
        this.mazelayout[curY][curX] = 4;
    }
    /**
     * 0 = wall
     * 1 = free space
     * 3 = player path
     * 4 = player
     */

    public int getCell(int y, int x){
        return mazelayout[y][x];
    }


    /**
     * parses the 2d array allowing it to displayed
     */
    public void displayMaze() {
        for (int i = 0; i < mazelayout.length; i++) {
            for (int j = 0; j < mazelayout[i].length; j++) {
                if (mazelayout[i][j] == 0) {
                    System.out.print("#");
                } else if (mazelayout[i][j] == 1) {
                    System.out.print(" ");
                } else if (mazelayout[i][j] == 3) {
                    System.out.print("~");
                } else if (mazelayout[i][j] == 4) {
                    System.out.print("@");
                }

            }
            System.out.println();
        }
    }

    public void findExit() {
        CheckExit();
        while (!isFinished){
            CheckExit();
            takeStep();
        }
        System.out.println();
    }

    public void takeStep()  {
        int tempX = curX;
        int tempY = curY;
        CheckExit();
        if(!isFinished){
            try {
                if (!(lookRight() == 0)) {
                    turnRight();
                    moveAHead();
                } else if (!(lookAHead() == 0)) {
                    moveAHead();
                } else if (!(lookLeft() == 0)) {
                    turnLeft();
                    moveAHead();
                } else {
                    turnAround();
                    moveAHead();
                }
                mazelayout[tempY][tempX] = 3;

            }catch (IndexOutOfBoundsException e){
                isFinished = true;
            }
        }
    }

    /**
     * Handling the rotation
     */
    public void turnRight() {
        dirindex = (dirindex + 1) % 4;
        curDir = dir[dirindex];
    }

    public void turnLeft() {
        dirindex = (dirindex + 3) % 4;
        curDir = dir[dirindex];
    }

    public void turnAround() {
        dirindex = (dirindex + 2) % 4;
        curDir = dir[dirindex];
    }

//  right x+1 , y
//  left  x-1 , y
//  up    x   , y-1
//  down  x   , y+1

    public int lookRight() {
        if (curDir.equals("North")) {
            return mazelayout[curY][curX + 1];
        } else if (curDir.equals("East")) {
            return mazelayout[curY + 1][curX];
        } else if (curDir.equals("South")) {
            return mazelayout[curY][curX - 1];
        } else {
            return mazelayout[curY - 1][curX];
        }
    }

    public int lookAHead() {
        if (curDir.equals("North")) {
            return mazelayout[curY - 1][curX];
        } else if (curDir.equals("East")) {
            return mazelayout[curY][curX + 1];
        } else if (curDir.equals("South")) {
            return mazelayout[curY + 1][curX];
        } else {
            return mazelayout[curY][curX - 1];
        }
    }

    public int lookLeft() {
        if (curDir.equals("North")) {
            return mazelayout[curY][curX - 1];
        } else if (curDir.equals("East")) {
            return mazelayout[curY - 1][curX];
        } else if (curDir.equals("South")) {
            return mazelayout[curY][curX + 1];
        } else {
            return mazelayout[curY + 1][curX];
        }
    }

    /**
     * Handle the movement based on where the characters is facing
     */
    private void moveAHead() {
        if (curDir.equals("North")) {
            curY--;
        } else if (curDir.equals("East")) {
            curX++;
        } else if (curDir.equals("South")) {
            curY++;
        } else {
            curX--;
        }
        mazelayout[curY][curX] = 4;
    }
    public boolean CheckExit() {
        if (curDir.equals("North") && curY == 0) {
            return isFinished = true;
        } else if (curDir.equals("East") && curX == mazelayout[0].length) {
            return isFinished = true;
        } else if (curDir.equals("South") && curY == mazelayout.length){
            return isFinished = true;
        } else if (curDir.equals("West") && curX == 0){
            return isFinished = true;
        }else {
            return false;
        }
    }
    public String getDirection(){
        return curDir;
    }
    public boolean isFinished() {
        return isFinished;
    }
}