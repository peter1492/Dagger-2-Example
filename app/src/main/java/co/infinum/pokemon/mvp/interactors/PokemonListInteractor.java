package co.infinum.pokemon.mvp.interactors;

import javax.inject.Inject;

import co.infinum.pokemon.models.Pokedex;
import co.infinum.pokemon.mvp.interfaces.MvpListener;
import co.infinum.pokemon.mvp.interfaces.MvpPokemonList;
import co.infinum.pokemon.network.PokemonService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by dino on 21/03/15.
 */
public class PokemonListInteractor implements MvpPokemonList.Interactor, Callback<Pokedex> {

    private MvpListener<Pokedex> pokemonListListener;

    private boolean isCanceled;

    private PokemonService pokemonService;

    @Inject
    public PokemonListInteractor(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @Override
    public void loadPokemonList(MvpListener<Pokedex> pokemonListListener) {
        reset();
        this.pokemonListListener = pokemonListListener;
        pokemonService.getPokedex(this);
    }

    @Override
    public void cancel() {
        isCanceled = true;
    }

    @Override
    public void reset() {
        isCanceled = false;
    }

    @Override
    public void success(Pokedex pokedex, Response response) {
        if (!isCanceled) {
            pokemonListListener.onSuccess(pokedex);
        }
    }

    @Override
    public void failure(RetrofitError error) {
        if (!isCanceled) {
            pokemonListListener.onFailure(error.getMessage());
        }
    }
}
