import java.util.Random;
public class Ga {
		    public double lower;        // �½�
		    public double upper;        // �Ͻ�
		    public int POP_SIZE;        // ��Ⱥ��Ŀ
		    public int M;               // ����λ��
		    public String[] pop;        // ��Ⱥ����
		    public double[] result;     // ��Ⱥ����Ľ��
		    public int Length;          // ���볤�ȣ���ΪҪ��ȷ��С�������λ�����볤��Ϊ22λ
		    public int MJ2;             // 2^22
		    public double[]fitness;     // �����Ⱥ��Ӧ��
		    public double PC;           // ������
		    public double PM;           // ������
		    public double[] p;            // ���̶ķ���������Ӧ�ȸ���
		    public double[] q;            // q[i]��ǰn��p֮��
		    public Random random;       // ���ڲ���������Ĺ���
		    public Best best;           // �����Ѵ𰸵Ķ���
		    public Ga(double lower, double upper, int POP_SIZE, int m, double PC, double PM, double d[]) {
		        this.lower = lower;
		        this.upper = upper;
		        this.POP_SIZE = POP_SIZE;
		        M = m;
		        this.pop = new String[this.POP_SIZE];
		        this.result = new double[this.POP_SIZE];
		        Length = 22;
		        this.MJ2 = 4194304;
		        this.fitness = new double[this.POP_SIZE];
		        this.PC = PC;
		        this.PM = PM;
		        this.p = new double[this.POP_SIZE];
		        this.q = new double[this.POP_SIZE];
		        this.random = new Random();
		        this.best = new Best();
		        for (int i=0; i<d.length; i++){
		            result[i] = d[i];
		        }
		    }

		    /**
		     *  ���뷽ʽ������ֵ��ʾΪ�������ֽ�����ʽ
		     */
		     public void encoding(){
		        for (int i=0; i<result.length; i++){
		            double d1 = ((result[i] - lower) / (upper - lower)) * (MJ2-1);
		            pop[i] = Integer.toBinaryString((int) d1);
		        }
		        for (int i=0; i<pop.length; i++){
		            if (pop[i].length() < Length){
		                int k = Length - pop[i].length();
		                for (int j=0; j<k; j++){
		                    pop[i] = "0"+pop[i];
		                }
		            }
		        }
		     }

		    /**
		     *  ���뷽ʽ�����������ֽ��뻹ԭ�ɽ�
		     */
		    public void decoding(){
		        for (int i=0; i<pop.length; i++){
		            int k = Integer.parseInt(pop[i], 2);
		            result[i] = lower + k * (upper - lower) / (MJ2 - 1);
		        }
		    }
		    /**
		     *  ��Ӧ�Ⱥ���
		     */
		    public void fitness(){
		        for (int i=0; i<result.length; i++){
		            fitness[i] = result[i] * (Math.sin(10 * Math.PI * result[i])) + 2;
		        }
		    }

		    /**
		     *  �������
		     */
		    public void crossover() {
		        for (int i = 0; i < POP_SIZE / 2; i++) {
		            double d = random.nextDouble();
		            if (d < PC) {
		                int k1 = random.nextInt(POP_SIZE);
		                int k2 = random.nextInt(POP_SIZE);
		                do {
		                    k1 = (int) random.nextInt(POP_SIZE);
		                    k2 = (int) random.nextInt(POP_SIZE);
		                } while (k1 == k2);
		                int position = random.nextInt(Length);
		                String s11 = null, s12 = null, s21 = null, s22 = null;
		                s11 = pop[k1].substring(0, position);
		                s12 = pop[k1].substring(position, Length);

		                s21 = pop[k2].substring(0, position);
		                s22 = pop[k2].substring(position, Length);

		                pop[k1] = s11 + s22;
		                pop[k2] = s21 + s12;
		            }
		        }
		    }

		    /**
		     *  �������
		     */
		    public void mutation(){
		        for (int i=0; i<pop.length; i++){
		            for (int j=0; j<Length; j++){
		                double k = random.nextDouble();
		                if (PM>k){
		                    mutation(i,j);
		                }
		            }
		        }
		    }
		    public void mutation(int i,int j)
		    {
		        String s=pop[i];
		        StringBuffer sb=new StringBuffer(s);
		        if(sb.charAt(j)=='0')
		            sb.setCharAt(j, '1');
		        else
		            sb.setCharAt(j, '0');
		        pop[i]=sb.toString();

		    }

		    /*
		     * ���̶ķ���
		     */
		    public void roulettewheel()
		    {
		        decoding();
		        fitness();

		        double sum=0;
		        for (int i = 0; i <POP_SIZE; i++) {
		            sum=fitness[i]+sum;
		        }
		        for (int i = 0; i < POP_SIZE; i++) {
		            p[i]=fitness[i]/sum;
		        }
		        for (int i = 0; i < POP_SIZE; i++) {
		            for (int j = 0; j < i+1; j++) {
		                q[i]=p[j];
		            }
		        }
		        double []ran=new double[POP_SIZE];
		        String[] tempPop=new String[POP_SIZE];
		        for (int i = 0; i < ran.length; i++) {
		            ran[i]=random.nextDouble();
		        }
		        for (int i = 0; i < ran.length; i++) {
		            int k = 0;
		            for (int j = 0; j < q.length; j++) {
		                if(ran[i]<q[j])
		                {
		                    k=j;
		                    break;
		                }
		                else continue;
		            }
		            tempPop[i]=pop[k];
		        }
		        for (int i = 0; i < tempPop.length; i++) {
		            pop[i]=tempPop[i];
		        }
		    }

		    /**
		     *  һ�ν���
		     */
		    public void evolution(){
		        encoding();
		        crossover();
		        mutation();
		        decoding();
		        fitness();
		        roulettewheel();
		        findResult();
		    }

		    /**
		     * ����n��
		     * @param n �����Ĵ���
		     */
		    public void dispose(int n)
		    {
		        for (int i = 0; i < n; i++) {
		            evolution();
		        }
		    }
		    /**
		     * ȡ�ý��
		     */
		    public double findResult()
		    {
		        if(best==null)
		            best=new Best();
		        double max=best.fitness;
		        for (int i = 0; i < fitness.length; i++) {
		            if(fitness[i]>max)
		            {
		                best.fitness=fitness[i];
		                best.x=result[i];
		                best.str=pop[i];
		                //System.out.println(best.fitness);
		            }
		        }
		        return max;
		    }

		    /**
		     *  ȡ��xֵ
		     * @return �����ݻ����xֵ
		     */
		    public double findx()
		    {
		        fitness();
		        double max=0;
		        int index=0;
		        for (int i = 0; i < fitness.length; i++) {
		            System.out.println(result[i]);
		            if(fitness[i]>max)
		            {
		                max=fitness[i];
		                index=i;
		            }
		        }
		        return result[index];
		    }

		}


	}

}
