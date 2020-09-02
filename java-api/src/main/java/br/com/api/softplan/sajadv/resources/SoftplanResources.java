/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.api.softplan.sajadv.resources;

import br.com.api.softplan.sajadv.entity.Pessoa;
import br.com.api.softplan.sajadv.exception.APIException;
import br.com.api.softplan.sajadv.response.Message;
import br.com.api.softplan.sajadv.response.ResponseModel;
import br.com.api.softplan.sajadv.service.Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.ObserverException;
import javax.inject.Inject;
import javax.servlet.annotation.WebFilter;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

@Path("pessoas")
@RequestScoped
@Api("Endpoints softplan")
@WebFilter(asyncSupported = true)
public class SoftplanResources {

    @EJB
    private Service service;

    @Inject
    Event<Pessoa> pessoaEvent;

    private Logger log = LogManager.getLogger();

    @ApiOperation(
            value = "Cadastrar pessoa",
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON)
    @ApiResponses(
            @ApiResponse(message = "",
                    code = 201,
                    response = ResponseModel.class))
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(Pessoa pessoa) {
        ResponseModel responseModel = new ResponseModel();

        try {
            pessoaEvent.fire(pessoa);
            responseModel.getData().add(service.inserirPessoa(pessoa));
            responseModel.getMessages().add(new Message(200, "OK!"));

        } catch (ObserverException ex) {
            if (ex.getCause() instanceof APIException) {
                APIException exc = (APIException) ex.getCause();
                responseModel.getMessages().add(new Message(exc.getCodigo(), exc.getDescricao()));
            }
            return Response.status(Response.Status.OK).entity(responseModel).build();
        } catch (Exception ex) {
            log.error(ex, ex);
            responseModel.getMessages().add(new Message(500, Response.Status.INTERNAL_SERVER_ERROR.toString()));
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseModel).build();
        }
        return Response.ok(responseModel).status(Response.Status.CREATED).build();
    }

    @ApiOperation(
            value = "Atualizar pessoa",
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON)
    @ApiResponses(
            @ApiResponse(message = "",
                    code = 200,
                    response = ResponseModel.class))
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizar(Pessoa pessoa) {
        ResponseModel responseModel = new ResponseModel();

        try {
            pessoaEvent.fire(pessoa);
            responseModel.getData().add(service.atualizar(pessoa));
            responseModel.getMessages().add(new Message(200, "OK!"));
        } catch (ObserverException ex) {
            if (ex.getCause() instanceof APIException) {
                APIException exc = (APIException) ex.getCause();
                responseModel.getMessages().add(new Message(exc.getCodigo(), exc.getDescricao()));
            }
            return Response.status(Response.Status.OK).entity(responseModel).build();
        } catch (Exception ex) {
            log.error(ex, ex);
            responseModel.getMessages().add(new Message(500, Response.Status.INTERNAL_SERVER_ERROR.toString()));
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseModel).build();
        }
        return Response.ok(responseModel).build();
    }

    @ApiOperation(value = "Deletar pessoa",
            produces = MediaType.APPLICATION_JSON)
    @ApiResponses(
            @ApiResponse(
                    code = 200,
                    message = "Pessoa removida com sucesso",
                    response = ResponseModel.class))

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response deletar(@PathParam("id") long id) {
        ResponseModel responseModel = new ResponseModel();
        try {
            responseModel.getData().add(service.exclusaoLogica(id));
            responseModel.getMessages().add(new Message(200, "OK!"));

        } catch (APIException ex) {
            responseModel.getMessages().add(new Message(ex.getCodigo(), ex.getDescricao()));
            return Response.status(Response.Status.NOT_FOUND).entity(responseModel).build();
        } catch (Exception ex) {
            log.error(ex, ex);
            responseModel.getMessages().add(new Message(500, Response.Status.INTERNAL_SERVER_ERROR.toString()));
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseModel).build();
        }
        return Response.ok(responseModel).build();
    }

    @ApiOperation(value = "Buscar pessoa por ID", produces = MediaType.APPLICATION_JSON)
    @ApiResponses(
            @ApiResponse(message = "",
                    code = 200,
                    response = ResponseModel.class
            ))
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorID(
            @ApiParam(
                    value = "ID (identificador) da pessoa",
                    name = "id",
                    required = true) @PathParam("id") @Required long id) {
        ResponseModel responseModel = new ResponseModel();
        try {
            Pessoa pessoa = service.buscarPorId(id);
            if (pessoa == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(responseModel).build();
            }
            responseModel.getData().add(pessoa);
            responseModel.getMessages().add(new Message(200, "OK!"));
        } catch (Exception ex) {
            log.error(ex, ex);
            responseModel.getMessages().add(new Message(500, Response.Status.INTERNAL_SERVER_ERROR.toString()));
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseModel).build();
        }
        return Response.ok(responseModel).build();
    }

    @ApiOperation(
            value = "Buscar pessoa por filtro",
            produces = MediaType.APPLICATION_JSON)
    @ApiResponses(
            @ApiResponse(message = "",
                    code = 200,
                    response = ResponseModel.class
            ))
    @GET
    @Path("filtro")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response buscar(@QueryParam("nome") String nome,
            @QueryParam("cpf") String cpf,
            @QueryParam("email") String email,
            @QueryParam("nascimento") String nascimento) {

        ResponseModel responseModel = new ResponseModel();

        try {
            responseModel.getData().add(service.buscarTodos(nome, cpf, email, nascimento));
            responseModel.getMessages().add(new Message(200, "OK!"));
        } catch (APIException ex) {
            responseModel.getMessages().add(new Message(ex.getCodigo(), ex.getDescricao()));
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseModel).build();
        } catch (Exception ex) {
            responseModel.getMessages().add(new Message(500, Response.Status.INTERNAL_SERVER_ERROR.toString()));
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseModel).build();
        }
        return Response.ok(responseModel).build();

    }
}
