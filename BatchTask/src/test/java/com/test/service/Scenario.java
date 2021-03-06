package com.test.service;

import org.junit.Before;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Uint;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.tx.gas.StaticGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Optional;

import static junit.framework.TestCase.fail;

/**
 * Common methods & settings used accross scenarios.
 */
public class Scenario extends BaseTest{

    static final BigInteger GAS_PRICE = BigInteger.valueOf(22_000_000_000L);
    static final BigInteger GAS_LIMIT = BigInteger.valueOf(210000);
    static final StaticGasProvider STATIC_GAS_PROVIDER =
            new StaticGasProvider(GAS_PRICE, GAS_LIMIT);

    // testnet
    static final String WALLET_PASSWORD = "123456";

    /*
    If you want to use regular Ethereum wallet addresses, provide a WALLET address variable
    "0x..." // 20 bytes (40 hex characters) & replace instances of ALICE.getAddress() with this
    WALLET address variable you've defined.
    */

    static final String CONTRACT_ADDRESS = "0x793367ccf4B27C8c05565dd6a6ddFd4D4619b1ac";

    static final String ORACLE_CONTRACT_ADDRESS = "0x1ca7ce64a84dd27bbe31f2021e95efa684603444";

    static final String SETH_CONTRACT_ADDRESS = "0x916b96d20ace37d4ff57f552843f35f61b84e775";

    static final String SDUSD_CONTRACT_ADDRESS = "0xc805c020502129c6047fc09cc97d75cda1e0d3fa";

    static final String SAR_CONTRACT_ADDRESS = "0x62273e9ec0da1dd04c882eacb05694a1d9b3aef3";

    private static String ALICE_KEY = "D:\\walletfile\\keystore\\801b653ba6e0292bafc975601d5329b59a375be6";

    Credentials ALICE;

    private static String BOB_KEY = "D:\\walletfile\\keystore\\fd9ecc02152397204b14c9d95de01bf4012a548d";
    Credentials BOB;

    private static String TOM_KEY = "D:\\walletfile\\keystore\\4e74cba89c96e65c2582d0b481931117352169d7";
    Credentials TOM;

    private static final BigInteger ACCOUNT_UNLOCK_DURATION = BigInteger.valueOf(30);

    private static final int SLEEP_DURATION = 15000;
    private static final int ATTEMPTS = 40;

    Admin web3j;

    Admin web3jws;
    public Scenario() { }

    @Before
    public void setUp() {
        try {
            this.web3j = Admin.build(new HttpService(RPC));
            this.web3jws = Admin.build(new WebSocketService(RPC_WS,true));
            this.ALICE = WalletUtils.loadCredentials(WALLET_PASSWORD, ALICE_KEY);
            this.BOB = WalletUtils.loadCredentials(WALLET_PASSWORD,BOB_KEY);
            this.TOM = WalletUtils.loadCredentials(WALLET_PASSWORD,TOM_KEY);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    boolean unlockAccount() throws Exception {
        PersonalUnlockAccount personalUnlockAccount =
                web3j.personalUnlockAccount(
                        ALICE.getAddress(), WALLET_PASSWORD, ACCOUNT_UNLOCK_DURATION)
                        .sendAsync().get();
        return personalUnlockAccount.accountUnlocked();
    }

    TransactionReceipt waitForTransactionReceipt(
            String transactionHash) throws Exception {

        Optional<TransactionReceipt> transactionReceiptOptional =
                getTransactionReceipt(transactionHash, SLEEP_DURATION, ATTEMPTS);

        if (!transactionReceiptOptional.isPresent()) {
            fail("Transaction receipt not generated after " + ATTEMPTS + " attempts");
        }

        return transactionReceiptOptional.get();
    }

    private Optional<TransactionReceipt> getTransactionReceipt(
            String transactionHash, int sleepDuration, int attempts) throws Exception {

        Optional<TransactionReceipt> receiptOptional =
                sendTransactionReceiptRequest(transactionHash);
        for (int i = 0; i < attempts; i++) {
            if (!receiptOptional.isPresent()) {
                Thread.sleep(sleepDuration);
                receiptOptional = sendTransactionReceiptRequest(transactionHash);
            } else {
                break;
            }
        }

        return receiptOptional;
    }

    private Optional<TransactionReceipt> sendTransactionReceiptRequest(
            String transactionHash) throws Exception {
        EthGetTransactionReceipt transactionReceipt =
                web3j.ethGetTransactionReceipt(transactionHash).sendAsync().get();

        return transactionReceipt.getTransactionReceipt();
    }

    BigInteger getNonce(String address) throws Exception {
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                address, DefaultBlockParameterName.LATEST).sendAsync().get();

        return ethGetTransactionCount.getTransactionCount();
    }

    Function createFibonacciFunction() {
        return new Function(
                "fibonacciNotify",
                Collections.singletonList(new Uint(BigInteger.valueOf(8))),
                Collections.singletonList(new TypeReference<Uint>() {}));
    }

    static String load(String filePath) throws URISyntaxException, IOException {
        URL url = Scenario.class.getClass().getResource(filePath);
        byte[] bytes = Files.readAllBytes(Paths.get(url.toURI()));
        return new String(bytes);
    }

    static String getFibonacciSolidityBinary() throws Exception {
        return load("/solidity/Fibonacci.bin");
    }
}
