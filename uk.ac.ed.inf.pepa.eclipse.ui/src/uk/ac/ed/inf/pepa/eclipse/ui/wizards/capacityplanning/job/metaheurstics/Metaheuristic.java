package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.metaheurstics;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.candidates.Candidate;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.labs.Parameters.MetaHeuristicParameters;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.job.recorders.Recorder;
import uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning.models.Config;

public abstract class Metaheuristic {
	
	protected ArrayList<Candidate> candidatePopulation;
	public Recorder recorder;
	protected IProgressMonitor monitor;
	
	protected MetaHeuristicParameters myParameters;
	protected Candidate candidate;
	
	protected int generationSize;
	protected int candidateSize;
	
	public Metaheuristic(MetaHeuristicParameters metaheuristicParameters, 
			Candidate candidate,
			IProgressMonitor monitor,
			Recorder recorder){
		
		this.myParameters = metaheuristicParameters;
		this.candidate = candidate; 
		
		this.generationSize = myParameters.getParameters().get(Config.GENERATION_S).intValue();
		this.candidateSize = myParameters.getParameters().get(Config.INITIALCANDIDATEPOPULATION_S).intValue();
		
		
		this.recorder = recorder;
		
		this.candidatePopulation = new ArrayList<Candidate>();
		
		this.monitor = monitor;
		
		createSeedPopulation();
		
	}
	
	/**
	 * copy seed candidate out into the population, and then scatter population away from seed
	 */
	public void createSeedPopulation(){
		
		for(int i = 0; i < candidateSize; i++){
			this.candidatePopulation.add(this.candidate.copySelf());
		}
		
		for(int i = 0; i < candidateSize; i++){
			this.candidatePopulation.get(i).scatter();
			this.candidatePopulation.get(i).setRecorder(recorder);
		}
		
		
	}
	
	public abstract IStatus search();


}
