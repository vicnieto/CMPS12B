import java.util.*;
import java.util.Scanner;
import java.io.*;
class Simulation {
	public static void main(String[] args) throws IOException{
		try { 
			if (args.length != 1) {
				System.out.println("Usage: Simultation <input file>");
				System.exit(1);
			}
			Scanner in = new Scanner(new File(args[0]));
			PrintWriter rep = new PrintWriter(new FileWriter(args[0] + ".rpt"));
			PrintWriter trace = new PrintWriter(new FileWriter(args[0] + ".trc"));
			}
		catch(FileNotFoundException e) {
			System.out.println(e);
			System.exit(1);
		}	
		Scanner in = new Scanner(new File(args[0]));
		PrintWriter rep = new PrintWriter(new FileWriter(args[0] + ".rpt"));
		PrintWriter trace = new PrintWriter(new FileWriter(args[0] + ".trc"));
		// will store all jobs in joblist queue
		Queue jobList = new Queue();
		// will store all processed jobs with finish times in finishedJobs queue
		Queue finishedJobs = new Queue();
		// new array to store all integers in input file
		ArrayList<Integer> input = new ArrayList();
		while(in.hasNextInt()){
			// add each integer in input file to the end of ArrayList input
			input.add(in.nextInt());
		}
		// the first number in the input file and therefore the first number in the arraylist input is the number of 
		// jobs in the input file. Store this in numJobs
		int numJobs = input.get(0);
		// obtain jobs from arraylist input
		for(int i = 1; i < input.size(); i++){
			if(i%2 == 0){
				Job x = new Job(input.get(i - 1), input.get(i));
				jobList.enqueue(x);
			}
		}
		
		rep.println("Report file: " + args[0] + ".rpt");
		rep.println(numJobs + " Jobs: ");
		rep.println(jobList);
		rep.println();
		rep.println("***********************************************************");
		trace.println("Trace file: " + args[0] + ".trc");
		trace.println(numJobs + " Jobs: ");
		trace.println(jobList);
		// total number of processors to simulate is always the number of jobs - 1
		int numProcessors = numJobs - 1;
			
		for(int i = 1; i <= numProcessors; i++){
			trace.println();
			trace.println(i + " processor(s): ");
			finishedJobs = processor(jobList, i, numJobs, trace);
			calcWait(finishedJobs, numJobs, rep, i);
			resetFinish(jobList);
			}
		
		in.close();
		rep.close();
		trace.close();
		
	}
	
	
	public static Queue processor(Queue jobs, int numProcessors, int numJobs, PrintWriter trace){
		// list of queues to store all the processor queues, the queue containing all jobs, and the queue containing finished jobs. 
		// This storage queue should be of length numProcessors + 2
		Queue[] storage = new Queue[numProcessors + 2];
		int time = 0;
		// variable to store the current job and next job and length of smallest processor queue
		Job jobc, jobn;
		int leastLen=1, leastInd = 0;
		// will store queue with jobs to be processed in the array storage at index lstp and will
		// store queue with finished jobs in the same array at the index lstf
		int lstp = numProcessors, lstf = numProcessors + 1;
		// adds numProcessors number of processor queues to the begining of storage array
		for(int i=0; i < numProcessors; i++){
			storage[i] = new Queue();
		}
		
		// make a copy of the jobs queue given in the parameters and place in the storage list at index lstp
		storage[lstp] = jobs.makeCopy();
		// last queue in array is queue with all finished jobs
		storage[lstf] = new Queue();
		// we know we are done with processing when all jobs are in finished job queue
		while(storage[lstf].length() != numJobs){
			// if time equals 0, then print initial state of processing
			if(time == 0){
				printTrace(time, storage, numProcessors, trace);
			}
			// add 1 to the current time
			time++;
			
			// if there are still jobs to be processed in the waiting queue in storage[lstp]
			// then change jobc to be the first job
			if(storage[lstp].length() != 0){
				// sets jobc equal to next job in processing list
				jobc = (Job)storage[lstp].peek();
				
				// if the arrival time of jobc is equal to the current time then
				if(jobc.getArrival() == time){
					// for loop to check length of all processor queues and find the index of queue
					// with the least length in order to place the next job with same arrival time there
					for(int i = numProcessors - 1; i >= 0; i--){
						if(storage[i].length() <= leastLen){
							leastLen = storage[i].length();
							leastInd = i;
						}
					}
					storage[leastInd].enqueue(storage[lstp].dequeue());
					// set least length equal to the length of the processor queue which you just added jobc 
					// to. That way when you look for the processor queue of least length the next time you
					// get the correct processor queue
					leastLen = storage[leastInd].length();
					
					// if the queue containing waiting jobs is not empty yet, check the next job in line to 
					// see if it has the same arrival time as the current time. if so place it in a 
					// processing queue and keep on checking the waiting jobs queue for jobs with the same 
					// arrival time
					if(storage[lstp].length() != 0){
						jobc = (Job)storage[lstp].peek();
						while(jobc.getArrival() == time){
							// for loop to check length of all processor queues and find the index of queue
							// with the least length in order to place the next job with same arrival time there
							for(int i = numProcessors - 1; i >= 0; i--){
								if(storage[i].length() <= leastLen){
									leastLen = storage[i].length();
									leastInd = i;
								}
							}
							// dequeue next job with same arrival from storage[lstp] and enqueueu it into processor
							// of least length
							storage[leastInd].enqueue(storage[lstp].dequeue());

							// set least length euqal to the length of the processor queue you just added to in order
							// to be able to find processor queue of least length next time around
							leastLen = storage[leastInd].length();

							// if there are still jobs to be processed in the waiting jobs list, change f to be the 
							// next job in order to check if it has the same arrival time as the previous job. 
							if(storage[lstp].length() != 0) jobc = (Job)storage[lstp].peek();
							// otherwise break out of the while loop. 
							else break;
						}
					}
					
				}
			}
							

			
			// goes through each processor queue and checks the first item. 
			// If the first item doesn't have a finish time it calculates it 
			for(int i = 0; i < numProcessors; i++){
				
				// if the processing queue has jobs in it, check the jobs arrival time
				if(storage[i].length() != 0){
					Job d = (Job)storage[i].peek();
					// if job d's arrival time is the same as the current time, then calculate its finish time
					if(d.getFinish() == -1){
						d.computeFinishTime(time);
					}
				}
			}
			
			// goes through each processor queue and checks if the finish time of the first
			// items is equal to the current time, if so it enqueues that job to storage[lstf]
			// where all the finished jobs are going to be enqueued
			for(int i = 0; i < numProcessors; i++){
				// if storage[i] queue is not empty check the first job in the queue to see if 
				// its finish time is equal to the current time
				if(storage[i].length() != 0){
					Job h = (Job)storage[i].peek();
						if(h.getFinish() == time){
							storage[lstf].enqueue(storage[i].dequeue());
						}
				}
			}

			// in the case that there is a job that finishes, we must calculate the finish time of 
			// the job that's right after the finished job
			for(int i = 0; i < numProcessors; i++){
				
				// if the processing queue has jobs in it, check the jobs arrival time
				if(storage[i].length() != 0){
					Job d = (Job)storage[i].peek();
					// if job d's arrival time is the same as the current time, then calculate its finish time
					if(d.getFinish() == -1){
						d.computeFinishTime(time);
					}
				}
			}
		
			// print the current state of processing
			printTrace(time, storage, numProcessors, trace);
		}

	return((Queue)storage[lstf]);
	}

	// resets the finish times of the jobs queue so that the finish times could be calculated again using 
	// different number of processors
	public static void resetFinish(Queue jobs){
		// store copy of jobs queue in temp
		Queue temp = jobs.makeCopy();
		// while the length of temp is not equal to zero continue to dequeue jobs from
		// the queue
		while(temp.length() != 0){
			// store jobs that are dequeued from temp in n and reset their finish times
			Job n = (Job)temp.dequeue();
			n.resetFinishTime();
		}
		return;
	}
	
	
	
	
	public static void printTrace(int time, Queue[] storage, int processors, PrintWriter trace){
		int lstp = processors, lstf = processors + 1;
		trace.println("time = " + time);
		trace.println("0: " + storage[lstp] + " " + storage[lstf]);
		for(int i=0; i < processors; i++){
			trace.println(i + 1 + ": " + storage[i]);
		}
	}
	
	public static void calcWait(Queue finJobs, int jobs, PrintWriter report, int processors){
		// new queue temp made so that we don't mess with finJobs
		Queue temp = finJobs.makeCopy();
		double totalWait = 0, maxWait = 0, avgWait, waitTime;
		Job jobc;
		double[] data;
		// while loop to go through each job in temp queue
		while(temp.length() != 0){
			// make jobc next job in temp queue
			jobc = (Job)temp.dequeue();
			// get the wait time for jobc
			waitTime = jobc.getWaitTime();
			// add jobc's wait time to the total wait time
			totalWait += waitTime;
			// if jobc's wait time is greater than the current maxt wait time, it becomes the new max wait time
			if(waitTime > maxWait) maxWait = waitTime;
		}
		// calculate the average wait time for all jobs 
		avgWait = totalWait / jobs;
		// print the wait times for the specific number of processors to the report file
		report.println(processors + " processor(s): totalWait=" + totalWait + ", maxWait=" + maxWait + ", averageWait=" + avgWait);
	}
	
}
