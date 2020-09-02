import React from 'react'
import styled from 'styled-components'
import { Roller } from 'react-awesome-spinners'
import DataTable from 'react-data-table-component'
import NavbarTop from '../components/NavbarFixedTop'
import NavbarLeft from '../components/NavbarLeft'
import Input from '../components/InputText'
import Button from '../components/ButtonRounded'
import axios from 'axios'
import { Pencil } from 'styled-icons/boxicons-regular/Pencil'
import { Trash } from 'styled-icons/boxicons-regular/Trash'
import ReactModal from 'react-modal'
import { Close } from 'styled-icons/remix-fill/Close'
import { Plus } from 'styled-icons/boxicons-regular/Plus'
import { Eraser } from 'styled-icons/fa-solid/Eraser'
import { URL_BASE_AMBIENTE } from "../services/base";

const Container = styled.div`
    flex: 1;
`;

const Content = styled.div`
    flex: 1;
    display: flex;
    flex-direction: column;
    margin-left: 280px;
    padding: 0 50px;
    padding-top: 124px;
`;

const CardTitle = styled.div`
    font-size: 24px;
    text-align: left;
    line-height: 2rem;
    font-family: 'Roboto', sans-serif;
    padding: 16px;
    background-color: #091E40;
    border-color: #091E40;
    border-radius: 4px;
    color: #fff;
`;

const CardContainer = styled.div`
    display: flex;
    background-color: #fff;
    border-radius: 4px;
    margin-top: 40px;
    padding: 20px;
    align-items: flex-end;
`;

const Row = styled.div`
    display: flex;
    flex-direction: row;
`;


const CloseIcon = styled(Close)`
    color: #116D3A;
    height: 20px;
    width: 20px;
`;

const PencilIcon = styled(Pencil)`
    color: #116D3A;
    height: 20px;
    width: 20px;
`;

const TrashIcon = styled(Trash)`
    color: #C1272D;
    height: 20px;
    width: 20px;
`;

const PlusIcon = styled(Plus)`
    color: #fff;
    height: 24px;
    width: 24px;
`;

const EraserIcon = styled(Eraser)`
    color: #fff;
    height: 24px;
    width: 24px;
`;
const InputLabel = styled.span`
    margin-left: 10px;
`;

const InputContainer = styled.div`
    display: flex;
    flex-direction: column;
`;
const TextColored = styled.h3`
    color: #116D3A;
`;

const Select = styled.select`
    width: 300px;
    height: 52px;
    font-size: 16px;
    border-radius: 4px;
    padding: 10px;
    margin: 10px;
    border-width: 1px;
    border-style: solid;
    border-color: #00000066;
    color: #444;
    ::placeholder {
        color: #00000080;
    }

    &:hover {
        border-color: #000000CC;
    }

    &:focus,
    &:active {
        outline: none;
        border-color: #009fc2;
        box-shadow: inset 0px 0px 0px 1px #009fc2;
        caret-color: #009fc2;
    }
`;

const Spinner = styled.div`
    display: flex;
    justify-content: center;
    margin-bottom: 20px;
`;


const modalStyles = {
    content: {
        top: '50%',
        left: '50%',
        right: 'auto',
        bottom: 'auto',
        marginRight: '-50%',
        transform: 'translate(-50%, -50%)',
        display: 'flex',
        flexDirection: 'column',
        padding: 0
    }
};

const ModalHeader = styled.div`
    flex: 1;
    display: flex;
    background-color: #116D3A;
    justify-content: space-between;
    align-items: center;
`;

const ModalTitle = styled.span`
    color: #fff;
    font-size: 16px;
    margin-left: 20px;
    text-transform: uppercase;
`;

const ModalBody = styled.form`
    padding: 40px 80px;
    display: flex;
    align-items: center;
    flex-direction: column;
`;

const ModalFooter = styled.div`
    display: flex;
    justify-content: center;
    padding-bottom: 20px;
`;
const customStyles = {
    rows: {
        style: {
            minHeight: '60px',
        }
    },
    headCells: {
        style: {
            paddingLeft: '8px',
            paddingRight: '8px',
            fontSize: '18px',
        },
    },
    cells: {
        style: {
            paddingLeft: '8px',
            paddingRight: '8px',
            fontSize: '16px',
        },
    },
};
ReactModal.setAppElement('#root');
export default class AuditoriaScreen extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            showModal: false,
            loading: false,
            data: []
        }
    }

    clear = () => {
        this.setState({
            nome: "",
            cpf: "",
            email: "",
            nascimento: "",

        })
    }
    submitForm = () => {
        this.setState({ loading: true });
        var URL = `${URL_BASE_AMBIENTE}/filtro?`;
        //  URL = `http://localhost:8080/sajadv/v1/pessoas/filtro?`
        var nome = this.state.nome != '' ? this.state.nome : null;
        var cpf = this.state.cpf != '' ? this.state.cpf : null;
        var email = this.state.email != '' ? this.state.email : null;
        var nascimento = this.state.nascimento != '' ? this.state.nascimento : null;

        axios.get(URL, {
            params: {
                nome: nome,
                cpf: cpf,
                email: email,
                nascimento: nascimento
            }
        }).then(res => {
            console.log(res.data.data[0])
            if (res.status !== 200) {
                this.setState({ loading: false });
            }
            this.setState({
                loading: false,
                data: res.data.data[0],
                messages: res.data.messages.map((message) => {
                    if (message.codigo !== 200) {
                        return {
                            codigo: message.codigo,
                            descricao: message.descricao
                        }
                    }

                })
            })
        }).catch(err => {
            console.log("Erro: " + err);
            this.setState({ loading: false, showErrorModal: true });
        });
    }
    handleChange = (event) => {
        this.setState({ [event.target.name]: event.target.value });
    }
    handleDelete = () => {
        var URL = `${URL_BASE_AMBIENTE}/${this.state.id}?`;
        if (this.state.opcao == 0) {
            axios.delete(URL)
                .then(res => {
                    console.log(res)
                    this.submitForm();
                    this.setState({ loading: false });
                    this.handleCloseConfirmationModal();
                }).catch(err => {
                    console.log('Erro: ' + err);
                    this.setState({ loading: false });
                    this.handleCloseConfirmationModal();
                });
        } else {
            URL = `http://softplan.localhost/sajadv/v1/pessoas`
            // URL = `http://localhost:8080/sajadv/v1/pessoas`

            var patternData = /^[0-9]{2}\/[0-9]{2}\/[0-9]{4}$/;
            if (!patternData.test(this.state.nascimento)) {
                alert("Digite a data no formato Dia/Mês/Ano");
                return false;
            }

            var dados = {
                id: this.state.id,
                nome: this.state.nome,
                cpf: this.state.cpf,
                email: this.state.email,
                nascimento: this.state.nascimento
            };
            //EDITAR
            if (this.state.opcao == 1) {
                axios.put(URL, dados)
                    .then(res => {
                        var message = res.data.messages[0];
                        this.setState({
                            errorApi: message.codigo !== 200 && message.codigo !== 500 ? true : false,
                            message: message.codigo !== 200 && message.codigo !== 500 ? res.data.messages[0].codigo + ' - ' + res.data.messages[0].descricao : '',
                            loading: false
                        });
                        this.submitForm();
                        if (!this.state.errorApi) {
                            this.handleCloseConfirmationModal();
                        }
                    }).catch(err => {
                        console.log('Erro: ' + err);
                        this.setState({ loading: false });
                        this.handleCloseConfirmationModal();
                    }).finally(() => {
                        if (this.state.errorApi) {
                            this.setState({ showErrorModal: true });
                        }
                    });
            } else {
                axios.post(URL, dados)
                    .then(res => {
                        var message = res.data.messages[0];
                        this.setState({
                            errorApi: message.codigo !== 200 && message.codigo !== 500 ? true : false,
                            message: message.codigo !== 200 && message.codigo !== 500 ? message.codigo + ' - ' + message.descricao : '',
                            loading: false
                        });
                        this.submitForm();
                        if (!this.state.errorApi) {
                            this.handleCloseConfirmationModal();
                        }
                    }).catch(err => {
                        console.log('Erro: ' + err);
                        this.setState({ loading: false });
                        this.handleCloseConfirmationModal();
                    }).finally(() => {
                        if (this.state.errorApi) {
                            this.setState({ showErrorModal: true });
                        }
                    });
            }
        }
    }

    handleOpenConfirmationModal = (row, opcao) => {
        console.log(opcao);
        //Insert
        if (opcao === 3) {
            this.setState({
                nome: "",
                cpf: "",
                email: "",
                nascimento: "",
                opcao: opcao,
                acao: "Cadastrar Pessoa",
                showConfirmationModal: true
            })
        } else {
            this.setState({
                id: row.id,
                nome: row.nome,
                cpf: row.cpf,
                email: row.email,
                nascimento: row.nascimento,
                opcao: opcao,
                acao: opcao == 0 ? "Excluir Pessoa" : "Editar Pessoa",
                showConfirmationModal: true
            })
        }

    }

    handleCloseConfirmationModal = () => {
        this.setState({ showConfirmationModal: false });
    }

    showErrorModal = (message) => {
        console.log(message);

        this.setState({
            codigoError: message.codigo,
            descricaoError: message.descricao,
        })
    }
    closeErrorModal = () => {
        this.setState({ showErrorModal: false });
    }



    render() {
        const { data, nome, cpf, email, nascimento, loading, edit } = this.state;

        const columns = ([
            {
                name: 'Nome',
                selector: 'nome',
            },
            {
                name: 'CPF',
                selector: 'cpf',
            },
            {
                name: 'Email',
                selector: 'email',
            },
            {
                name: 'Nascimento',
                selector: 'nascimento',
            },
            {
                selector: 'edit',
                cell: row => <Button outlined='#116D3A' title={<PencilIcon />} click={() => this.handleOpenConfirmationModal(row, 1)} />,
                ignoreRowClick: true,
                allowOverflow: true,
                button: true,
            },
            {
                selector: 'remove',
                cell: row => <Button outlined='#C1272D' title={<TrashIcon />} click={() => this.handleOpenConfirmationModal(row, 0)} />,
                ignoreRowClick: true,
                allowOverflow: true,
                button: true,
            }
        ]);


        return (
            <Container>
                <NavbarTop />
                <NavbarLeft />
                <Content>
                    <CardTitle>GESTÃO DE PESSOAS</CardTitle>
                    <CardContainer>
                        <InputContainer>
                            <InputLabel>Nome</InputLabel>
                            <Input type='text' placeholder='Nome' name='nome' value={nome} change={this.handleChange} />
                        </InputContainer>
                        <InputContainer>
                            <InputLabel>CPF</InputLabel>
                            <Input type='number' placeholder='cpf' name='cpf' value={cpf} change={this.handleChange} />
                        </InputContainer>
                        <InputContainer>
                            <InputLabel>Email</InputLabel>
                            <Input type='text' placeholder='email' name='email' value={email} change={this.handleChange} />
                        </InputContainer>
                        <InputContainer>
                            <InputLabel>Nascimento</InputLabel>
                            <Input type='text' placeholder='dd/mm/yyyy' data-date-format="DD MMMM YYYY" name='nascimento' value={nascimento} change={this.handleChange} format="dd/MM/yyyy" />
                        </InputContainer>
                        <Button type='button' title='filtrar' click={this.submitForm} />
                        <Button type='button' title={<EraserIcon />} click={() => this.clear()} />
                        <Button title={<PlusIcon />} click={() => this.handleOpenConfirmationModal(null, 3)} />

                    </CardContainer>
                    <CardContainer>
                        {
                            loading && <Spinner><Roller /></Spinner>
                        }
                        {
                            <DataTable
                                noHeader={true}
                                columns={columns}
                                data={data}
                                paginationRowsPerPageOptions={[10, 25, 50, 100]}
                                paginationPerPage={10}
                                pagination={true}
                                noDataComponent={''}
                                customStyles={customStyles}
                                style={{}}
                            />
                        }
                    </CardContainer>
                </Content>
                <ReactModal
                    isOpen={this.state.showConfirmationModal}
                    style={modalStyles}
                    contentLabel={this.state.acao}
                    onRequestClose={this.handleCloseConfirmationModal}>
                    <ModalHeader>
                        <ModalTitle><h3>{this.state.acao}</h3></ModalTitle>
                        <Button outlined='#116D3A' title={<CloseIcon />} click={this.handleCloseConfirmationModal} />
                    </ModalHeader>
                    <ModalBody>
                        <InputContainer>
                            <InputLabel>Nome</InputLabel>
                            <Input type='text' placeholder='Nome' name='nome' value={this.state.nome} change={this.handleChange} disabled={this.state.opcao == 0 ? "disabled" : ""} />
                        </InputContainer>
                        <InputContainer>
                            <InputLabel>CPF</InputLabel>
                            <Input type='number' placeholder='cpf' name='cpf' value={this.state.cpf} change={this.handleChange} disabled={this.state.opcao == 0 ? "disabled" : ""} />
                        </InputContainer>
                        <InputContainer>
                            <InputLabel>Email</InputLabel>
                            <Input type='text' placeholder='email' name='email' value={this.state.email} change={this.handleChange} disabled={this.state.opcao == 0 ? "disabled" : ""} />
                        </InputContainer>
                        <InputContainer>
                            <InputLabel>Nascimento</InputLabel>
                            <Input type='text' placeholder='dd/mm/yyyy' name='nascimento' value={this.state.nascimento} change={this.handleChange} disabled={this.state.opcao == 0 ? "disabled" : ""} />
                        </InputContainer>
                        <TextColored>Confirma?</TextColored>
                    </ModalBody>
                    <ModalFooter>
                        <Button type='button' title='cancelar' click={this.handleCloseConfirmationModal} />
                        <Button type='submit' title='salvar' color='#C1272D' click={this.handleDelete} />
                    </ModalFooter>
                </ReactModal>

                <ReactModal
                    isOpen={this.state.showErrorModal}
                    style={modalStyles}
                    contentLabel='Erro'
                    onRequestClose={this.closeErrorModal}>
                    <ModalHeader>
                        <ModalTitle><h3>{this.state.acao}</h3></ModalTitle>
                    </ModalHeader>
                    <ModalBody>
                        <TextColored>{this.state.message}</TextColored>
                    </ModalBody>
                    <ModalFooter>
                        <Button type='button' title='FECHAR' click={this.closeErrorModal} />
                    </ModalFooter>
                </ReactModal>
            </Container>
        )
    }
}