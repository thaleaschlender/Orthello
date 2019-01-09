public interface EvaluationFunction {
    int evaluateBoard(Board board);
    void setW1(int w1);
    void setW2(int w2);
    void setW3(int w3);
    int getW1();
    int getW2();
    int getW3();
}
