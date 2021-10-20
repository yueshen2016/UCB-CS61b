public class NBody{
	public static double readRadius(String filename){
		In in = new In(filename);
		int numberOfPlanets = in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Planet[] readPlanets(String filename){
		In in = new In(filename);
		int numberOfPlanets = in.readInt();
		double radius = in.readDouble();
		Planet[] bodies = new Planet[numberOfPlanets];
		int i = 0;
		while(i < numberOfPlanets){
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String img = in.readString();
			bodies[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, img);
			i++;
		}
		return bodies;
	}

	public static void main(String[] args){
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = readRadius(filename);
		Planet[] bodies = readPlanets(filename);

		StdDraw.enableDoubleBuffering();
		StdDraw.setScale((-1) * radius, radius);

		double time = 0;
		while (time <= T){
			double xForces[] = new double[bodies.length];
			double yForces[] = new double[bodies.length];
			for (int i = 0; i < bodies.length; i++){
				xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
				yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
			}
			for (int i = 0; i < bodies.length; i++){
				bodies[i].update(dt, xForces[i], yForces[i]);
			}
			StdDraw.clear();
			StdDraw.picture(0,0,"images/starfield.jpg");
			for(Planet b:bodies){
				b.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);

			time += dt;
		}
		StdOut.printf("%d\n", bodies.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  			bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                  			bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
		}
	}
}