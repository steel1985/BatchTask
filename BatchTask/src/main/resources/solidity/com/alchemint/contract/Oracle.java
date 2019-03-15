package com.alchemint.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint128;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.1.1.
 */
public class Oracle extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b5b5b336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b5b5b610e6b806100636000396000f300606060405236156100ad576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680632b2e05c1146100b25780633e8f5b9014610103578063524f38891461018157806364e72dbd146102165780636b9f96ea1461026757806379502c551461027c5780638da5cb5b146102cf578063b44bd51d14610324578063bef6ff3314610395578063e74254a7146103e6578063f2fde38b14610476575b600080fd5b34156100bd57600080fd5b6100e9600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506104af565b604051808215151515815260200191505060405180910390f35b341561010e57600080fd5b610167600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001909190505061056f565b604051808215151515815260200191505060405180910390f35b341561018c57600080fd5b6101dc600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610701565b60405180826fffffffffffffffffffffffffffffffff166fffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561022157600080fd5b61024d600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610794565b604051808215151515815260200191505060405180910390f35b341561027257600080fd5b61027a6107b4565b005b341561028757600080fd5b61028f610a3d565b6040518088815260200187815260200186815260200185815260200184815260200183815260200182815260200197505050505050505060405180910390f35b34156102da57600080fd5b6102e2610a6d565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561032f57600080fd5b61037f600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610a92565b6040518082815260200191505060405180910390f35b34156103a057600080fd5b6103cc600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610b09565b604051808215151515815260200191505060405180910390f35b34156103f157600080fd5b61045c600480803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919080356fffffffffffffffffffffffffffffffff16906020019091905050610bc9565b604051808215151515815260200191505060405180910390f35b341561048157600080fd5b6104ad600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610d9f565b005b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561050c57600080fd5b6001600a60008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff021916908315150217905550600190505b5b919050565b6000600a60003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff1615156105c957600080fd5b816008846040518082805190602001908083835b60208310151561060357805182525b6020820191506020810190506020830392506105dd565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020819055503373ffffffffffffffffffffffffffffffffffffffff167f52464b6f68ead205e69f8601325cb933973101cfc4478b33606d7db3dbb2654b84846040518080602001838152602001828103825284818151815260200191508051906020019080838360005b838110156106bb5780820151818401525b60208101905061069f565b50505050905090810190601f1680156106e85780820380516001836020036101000a031916815260200191505b50935050505060405180910390a2600190505b92915050565b60006009826040518082805190602001908083835b60208310151561073c57805182525b602082019150602081019050602083039250610716565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060009054906101000a90046fffffffffffffffffffffffffffffffff1690505b919050565b600a6020528060005260406000206000915054906101000a900460ff1681565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561080f57600080fd5b60e0604051908101604052806108596040805190810160405280601581526020017f6c69717569646174655f6c696e655f726174655f630000000000000000000000815250610a92565b815260200161089c6040805190810160405280601481526020017f6c69717569646174655f6469735f726174655f63000000000000000000000000815250610a92565b81526020016108df6040805190810160405280600a81526020017f6665655f726174655f6300000000000000000000000000000000000000000000815250610a92565b81526020016109226040805190810160405280601481526020017f6c69717569646174655f746f705f726174655f63000000000000000000000000815250610a92565b81526020016109656040805190810160405280601681526020017f6c69717569646174655f6c696e655f72617465545f6300000000000000000000815250610a92565b81526020016109a86040805190810160405280600d81526020017f69737375696e675f6665655f6300000000000000000000000000000000000000815250610a92565b81526020016109eb6040805190810160405280600a81526020017f646562745f746f705f6300000000000000000000000000000000000000000000815250610a92565b8152506001600082015181600001556020820151816001015560408201518160020155606082015181600301556080820151816004015560a0820151816005015560c082015181600601559050505b5b565b60018060000154908060010154908060020154908060030154908060040154908060050154908060060154905087565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60006008826040518082805190602001908083835b602083101515610acd57805182525b602082019150602081019050602083039250610aa7565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390205490505b919050565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610b6657600080fd5b6000600a60008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff021916908315150217905550600190505b5b919050565b6000600a60003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff161515610c2357600080fd5b816009846040518082805190602001908083835b602083101515610c5d57805182525b602082019150602081019050602083039250610c37565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060006101000a8154816fffffffffffffffffffffffffffffffff02191690836fffffffffffffffffffffffffffffffff1602179055503373ffffffffffffffffffffffffffffffffffffffff167f52464b6f68ead205e69f8601325cb933973101cfc4478b33606d7db3dbb2654b84846040518080602001836fffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015610d595780820151818401525b602081019050610d3d565b50505050905090810190601f168015610d865780820380516001836020036101000a031916815260200191505b50935050505060405180910390a2600190505b92915050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610dfa57600080fd5b806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b5b505600a165627a7a7230582036425cfb9430626843663214ea14aeff7bad8af20a1036ad4e557a39a36d3d870029";

    public static final String FUNC_SETAUTH = "setAuth";

    public static final String FUNC_SETCONFIG = "setConfig";

    public static final String FUNC_GETPRICE = "getPrice";

    public static final String FUNC_AUTHS = "auths";

    public static final String FUNC_FLUSH = "flush";

    public static final String FUNC_CONFIG = "config";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_GETCONFIG = "getConfig";

    public static final String FUNC_RELEASEAUTH = "releaseAuth";

    public static final String FUNC_SETPRICE = "setPrice";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event ORACLEOPERATED_EVENT = new Event("OracleOperated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Oracle(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Oracle(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Oracle(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Oracle(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> setAuth(String addr) {
        final Function function = new Function(
                FUNC_SETAUTH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setConfig(String key, BigInteger set) {
        final Function function = new Function(
                FUNC_SETCONFIG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(key), 
                new org.web3j.abi.datatypes.generated.Uint256(set)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getPrice(String key) {
        final Function function = new Function(FUNC_GETPRICE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint128>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> auths(String param0) {
        final Function function = new Function(FUNC_AUTHS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> flush() {
        final Function function = new Function(
                FUNC_FLUSH, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple7<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>> config() {
        final Function function = new Function(FUNC_CONFIG, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple7<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>(
                new Callable<Tuple7<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple7<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue());
                    }
                });
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> getConfig(String key) {
        final Function function = new Function(FUNC_GETCONFIG, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> releaseAuth(String addr) {
        final Function function = new Function(
                FUNC_RELEASEAUTH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setPrice(String key, BigInteger set) {
        final Function function = new Function(
                FUNC_SETPRICE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(key), 
                new org.web3j.abi.datatypes.generated.Uint128(set)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<OracleOperatedEventResponse> getOracleOperatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ORACLEOPERATED_EVENT, transactionReceipt);
        ArrayList<OracleOperatedEventResponse> responses = new ArrayList<OracleOperatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OracleOperatedEventResponse typedResponse = new OracleOperatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.opType = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.opValue = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OracleOperatedEventResponse> oracleOperatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, OracleOperatedEventResponse>() {
            @Override
            public OracleOperatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ORACLEOPERATED_EVENT, log);
                OracleOperatedEventResponse typedResponse = new OracleOperatedEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.opType = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.opValue = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OracleOperatedEventResponse> oracleOperatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ORACLEOPERATED_EVENT));
        return oracleOperatedEventFlowable(filter);
    }

    @Deprecated
    public static Oracle load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Oracle(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Oracle load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Oracle(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Oracle load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Oracle(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Oracle load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Oracle(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Oracle> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Oracle.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Oracle> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Oracle.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Oracle> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Oracle.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Oracle> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Oracle.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class OracleOperatedEventResponse {
        public Log log;

        public String from;

        public String opType;

        public BigInteger opValue;
    }
}
