
public class Run {
    public static void main(String[] args) {
        //d为初试种群
        double d[]={-0.953181,-0.851234,-0.749723,-0.645386,-0.551234,-0.451644,-0.351534,-0.239566,-0.151234,0.145445,
                0.245445,0.285174,0.345445,0.445445,0.542445,0.645445,0.786445,0.845445,0.923238,1.245445,
                1.383453,1.454245,1.584566,1.644345,1.741545,1.845445,1.981254,-0.012853,0.083413,1.801231};
        double lower = -1;
        double upper = 2;
        int POP_size = 30;
        int M = 22;
        double PC = 0.95;
        double PM = 0.05;
        Ga ga = new Ga(lower, upper, POP_size,M, PC, PM, d);
        System.out.println("种群进化中....");
        //进化，这里进化10000次
        ga.dispose(10000);
        System.out.println("+++++++++++++++++++++++++++结果为：");
        System.out.println("x="+ga.best.x);
        System.out.println("f="+ga.best.fitness);
    }
}
