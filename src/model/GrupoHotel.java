package model;

import java.util.ArrayList;
import java.util.List;

public class GrupoHotel implements IEntidadeHoteleira
{
    private String nome;
    private List<IEntidadeHoteleira> filhos = new ArrayList<>();

    public GrupoHotel(String nome)
    {
        this.nome = nome;
    }

    public void adicionar(IEntidadeHoteleira entidade)
    {
        filhos.add(entidade);
    }

    public void remover(IEntidadeHoteleira entidade)
    {
        filhos.remove(entidade);
    }

    @Override
    public String getNome()
    {
        return this.nome;
    }

    @Override
    public void exibirDetalhes()
    {
        System.out.println("\n--- Grupo Hoteleiro: " + getNome() + " ---");

        for (IEntidadeHoteleira filho : filhos)
        {
            filho.exibirDetalhes();
        }

        System.out.println("--- Fim do Grupo: " + getNome() + " ---");
    }
}