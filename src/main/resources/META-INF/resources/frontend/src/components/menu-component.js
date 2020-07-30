import {html, PolymerElement} from '@polymer/polymer';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';

class MenuComponent extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout style="height: 100%; align-items: stretch; background-color: var(--lumo-contrast-90pct); width: 100%;">
 <vaadin-button theme="primary success" id="mainViewButton">
   Main View 
 </vaadin-button>
 <vaadin-button theme="primary success" id="searchRecipeButton">
   Search a Recipe 
 </vaadin-button>
 <vaadin-button theme="primary success" id="createRecipeButton">
   Create a Recipe 
 </vaadin-button>
 <vaadin-button theme="primary success" id="myRecipesButton">
  My Created Recipes
 </vaadin-button>
 <vaadin-button theme="primary success" id="myFavouriteButton">
  My Favourite Recipes
 </vaadin-button>
 <vaadin-button theme="primary success" id="myFridgeButton">
   My Fridge 
 </vaadin-button>
 <vaadin-button theme="primary success" id="myAccountButton">
   Account Details 
 </vaadin-button>
 <vaadin-button theme="primary error" id="signOutButton">
   Sign Out 
 </vaadin-button>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'menu-component';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(MenuComponent.is, MenuComponent);
