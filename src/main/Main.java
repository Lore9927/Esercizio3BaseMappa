package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import entities.Articolo;

public class Main 
{

	public static void main(String[] args) 
	{
		Map<String,Articolo> articoli = new LinkedHashMap<>();
		String codice;
		String descrizione;
		int quantita;
		String risposta = "";
		Scanner tastiera = new Scanner(System.in);
		String[] rigaFile;
		Scanner file = null;
		try 
		{
			file = new Scanner(new File("src/res/Articoli.csv"));	
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		while(file.hasNextLine())
		{
			rigaFile = file.nextLine().split(";");
			Articolo a = new Articolo();
			a.setId(Integer.parseInt(rigaFile[0]));
			a.setCodice(rigaFile[1]);
			a.setDescrizione(rigaFile[2]);
			a.setQuantita(Integer.parseInt(rigaFile[3]));
			
			articoli.put(a.getCodice(), a);
		}
		file.close();
		
		for(Map.Entry<String, Articolo> m : articoli.entrySet())
			for(Articolo a : articoli.values())
				System.out.println("Id:" 		+ a.getId()		+ "\n" + 
					   	   "Codice: " 		+ a.getCodice()		+ "\n" +
					   	   "Descrizione: " 	+ a.getDescrizione()	+ "\n" +
					           "Quantità: " 	+ a.getQuantita() 	+ "\n");
		
		System.out.println("Vuoi aggiungere un nuovo articolo?\ns/n");
		risposta = tastiera.nextLine();
		
		if(risposta.equalsIgnoreCase("s"))
		{
			System.out.println("Inserisci il codice del nuovo articolo ");
			codice = tastiera.nextLine();

			System.out.println("Inserisci la descrizione del nuovo articolo ");
			descrizione = tastiera.nextLine();

			System.out.println("Inserisci la quantità del nuovo articolo " );
			quantita = Integer.parseInt(tastiera.nextLine());

			Articolo a = new Articolo();
			a.setId(articoli.size()+1);
			a.setCodice(codice);
			a.setDescrizione(descrizione);
			a.setQuantita(quantita);
			articoli.put(a.getCodice(),a);
			tastiera.close();
			try (PrintWriter writer = new PrintWriter(new File("src/res/nuovi-articoli.csv"))) 
			{
				for(Map.Entry<String, Articolo> m : articoli.entrySet())
				{
					StringBuilder sb = new StringBuilder();

					sb.append(m.getValue().getId());
					sb.append(';');
					sb.append(m.getValue().getCodice());
					sb.append(';');
					sb.append(m.getValue().getDescrizione());
					sb.append(';');
					sb.append(m.getValue().getQuantita());
					sb.append('\n');

					writer.write(sb.toString());
				}
				System.out.println("Scrittura file completata");

			} 
			catch (FileNotFoundException e) 
			{
				System.out.println(e.getMessage());
			}
		}
	}

}
