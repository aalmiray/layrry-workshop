/**
 * Copyright 2020-2021 The ModiTect authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.moditect.layrry.examples.links.membership.internal;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.moditect.layrry.examples.links.core.spi.RouterContributor;

public class MembersRouterContributor implements RouterContributor {

    private MembersService membersService = new MembersService();

    @Override
    public void install(Vertx vertx, RouterContributions contributions) {
        Router router = Router.router(vertx);

        router.get("/:memberID").handler(this::handleGetMember);
        router.put("/:memberID").handler(this::handleAddMember);
        router.get("/").handler(this::handleListMembers);

        contributions.add("/members", router);
    }

    private void handleGetMember(RoutingContext routingContext) {
        String memberID = routingContext.request().getParam("memberID");
        HttpServerResponse response = routingContext.response();

        if (memberID == null) {
            sendError(400, response);
        } else {
            JsonObject member = membersService.getMember(memberID);
            if (member == null) {
                sendError(404, response);
            } else {
                response.putHeader("content-type", "application/json").end(member.encodePrettily());
            }
        }
    }

    private void handleAddMember(RoutingContext routingContext) {
        String memberID = routingContext.request().getParam("memberID");
        HttpServerResponse response = routingContext.response();

        if (memberID == null) {
            sendError(400, response);
        } else {
            JsonObject member = routingContext.getBodyAsJson();
            if (member == null) {
                sendError(400, response);
            } else {
                membersService.addMember(memberID, member);
                response.end();
            }
        }
    }

    private void handleListMembers(RoutingContext routingContext) {
        routingContext.response()
            .putHeader("content-type", "application/json")
            .end(membersService.getMembers().encodePrettily());
    }

    private void sendError(int statusCode, HttpServerResponse response) {
        response.setStatusCode(statusCode).end();
    }
}
