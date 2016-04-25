package br.vizzatech;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import br.com.vizzatech.cryptocipher.CryptoXCipher;

public class TestCriptografiaUTF8
{

    private String        senha              = "MEUTESTE123";
    private String        senhaCriptografada = null;
    private String        salt               = "TesteThisAfter";
    private String        salt2              = "TesteThisBefore";
    private int           cesarCipher;
    private CryptoXCipher x;
    private CryptoXCipher x2;

    @Before
    public void setUp()
    {
        Random random = new Random();
        cesarCipher = random.nextInt(128);
        x = CryptoXCipher.getInstance(salt, "BLOWFISH", cesarCipher);
        x2 = CryptoXCipher.getInstance(salt2, "BLOWFISH", cesarCipher);
        assertNotNull(x);
        assertNotNull(x2);
        senhaCriptografada = x.cryptNoException(senha);
    }

    @Test
    public void criptografia()
    {
        assertNotNull(senhaCriptografada);
        assertFalse(senha.equals(senhaCriptografada));
    }

    @Test
    public void descriptografa()
    {
        String senhaDescriptografada = x.decryptNoException(senhaCriptografada);
        assertNotNull(senhaDescriptografada);
        assertEquals(senha, senhaDescriptografada);
    }

    @Test
    public void metodoChecaSenha()
    {
        assertTrue(x.validaTexto(senha, senhaCriptografada));
    }

    @Test
    public void metodoOutraInstancia()
    {
        String senhaCriptografada2 = x2.cryptNoException(senha);
        assertNotSame(senhaCriptografada, senhaCriptografada2);
        assertFalse(x.validaTexto(senha, senhaCriptografada2));
        assertTrue(x2.validaTexto(senha, senhaCriptografada2));
        assertEquals(senha, x2.decryptNoException(senhaCriptografada2));
    }
}