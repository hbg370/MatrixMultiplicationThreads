import java.util.concurrent.*;

public class MatrixMultiplication {
	final static int M  = 2000;
	private static double[][] matrix1 = new double[M][M];
	private static double[][] matrix2 = new double [M][M];
	private static double[][] result2 = new double[matrix1.length][matrix2[0].length];
	private static Parallel multiply = new Parallel();
	static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	//work from threadpool is "divided" between each of the cores, should be x times faster than
	//sequential multiplication, with x depending on number of cores interpreted by the machine used...

	public static void main(String[] args) {

		for (int i = 0; i < matrix1.length; i++)
			for (int j = 0; j < matrix1[i].length; j++)
				matrix1[i][j] = 1;
		
		for (int i = 0; i < matrix2.length; i++)
			for (int j = 0; j < matrix2[i].length; j++)
				matrix2[i][j] = 1;
		
		System.out.println("The number of processors is " + Runtime.getRuntime().availableProcessors());
		
		long startTime = System.currentTimeMillis();
		double[][] result = multiplyMatrix(matrix1, matrix2);
		long endTime = System.currentTimeMillis();
		System.out.println("Sequential time is " + (endTime - startTime) + " milliseconds");
	
		startTime = System.currentTimeMillis();
		parallelMultiplyMatrix();	
		endTime = System.currentTimeMillis();
		System.out.println("Parallel time is " + (endTime - startTime) + " milliseconds");
		executor.shutdownNow();
		
	}
	
	public static double[][] multiplyMatrix(double[][] a, double[][] b)
	{
		double[][] result = new double[a.length][b[0].length];
		for (int i = 0; i < result.length; i++)
			for (int j = 0; j < result[0].length; j++)
				for (int k = 0; k < a[0].length; k++)
					result[i][j] += a[i][k] * b[k][j];
		
		return result;
		
	}
	
	public static void parallelMultiplyMatrix()
	{
		for(int i = 0; i < M; i++)
			executor.execute(new Parallel());
		
		//executor.shutdown();

	}
	
	private static class Parallel extends Thread{
		
		public void run(){
			for (int i = 0; i < result2.length; i++)
				for (int j = 0; j < result2[0].length; j++)
					for (int k = 0; k < matrix1[0].length; k++)
					result2[i][j] += matrix1[i][k] * matrix2[k][j];
				}
		
		}
		
}

