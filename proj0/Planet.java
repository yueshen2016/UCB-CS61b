public class Planet{
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	static final double G = 6.67 * Math.pow(10, -11);

	public Planet(double xP, double yP, double xV, double yV, double m, String img){
		this.xxPos = xP;
		this.yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet b){
		this.xxPos = b.xxPos;
		this.yyPos = b.yyPos;
		this.xxVel = b.xxVel;
		this.yyVel = b.yyVel;
		this.mass = b.mass;
		this.imgFileName = b.imgFileName;
	}

	public double calcDistance(Planet b){
		double dis = Math.sqrt(Math.pow((this.xxPos - b.xxPos),2) + Math.pow((this.yyPos - b.yyPos),2));
		return dis;
	}

	public double calcForceExertedBy(Planet b){
		double force = G * this.mass * b.mass /Math.pow(this.calcDistance(b),2);
		return force;
	}

	public double calcForceExertedByX(Planet b){
		double disX = b.xxPos - this.xxPos;
		return disX / this.calcDistance(b) * this.calcForceExertedBy(b);
	}

	public double calcForceExertedByY(Planet b){
		double disY = b.yyPos - this.yyPos;
		return disY / this.calcDistance(b) * this.calcForceExertedBy(b);
	}

	public double calcNetForceExertedByX(Planet[] allBodys){
		double netForce = 0;
		for (int i = 0; i < allBodys.length; i ++){
			if (this.equals(allBodys[i]))
				continue;
			netForce += this.calcForceExertedByX(allBodys[i]);
		}
		return netForce;
	}

	public double calcNetForceExertedByY(Planet[] allBodys){
		double netForce = 0;
		for (int i = 0; i < allBodys.length; i++){
			if (this.equals(allBodys[i]))
				continue;
			netForce += this.calcForceExertedByY(allBodys[i]);
		}
		return netForce;
	}

	public void update(double dt, double xForce, double yForce){
		double xAcc = xForce / this.mass;
		double yAcc = yForce / this.mass;
		this.xxVel += dt * xAcc;
		this.yyVel += dt * yAcc;
		this.xxPos += dt * this.xxVel;
		this.yyPos += dt * this.yyVel;
	}

	public void draw(){
		StdDraw.enableDoubleBuffering();
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
	}
}